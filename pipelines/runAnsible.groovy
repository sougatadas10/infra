@Library('sharedLibrary')_
node() {
	properties([
		buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '2',daysToKeepStr: '',numToKeepStr: '2')),
		parameters([
			string(name: 'flavor',defaultValue: '',description: 'Possible values:standalone,cluster' ),
			string(name: 'notifiers',defaultValue: '',description: 'list of email ids to be notified in case of failure' ),
			string(name: 'inventory',defaultValue: '',description: 'inventory file name to be used by ansible' ),
			string(name: 'playbook',defaultValue: '',description: 'name of ansible playbook' ),
			string(name: 'extra_vars',defaultValue: '',description: 'list of extra variables. To be passed as json' ),
			string(name: 'commit_id',defaultValue: '',description: 'commit id' )
		])
	])
	stage('clone') {
		//git branch: 'main', changelog: false, poll: false, url: 'https://github.com/sougatadas10/pipelines.git'
		gitCheckout(branch: params.commit_id,url: "https://github.com/sougatadas10/infra.git")
	}
	stage('run ansible') {

		//sh "ansible-playbook -i $params.inventory --extra-vars $extraVars $params.playbook --connection=local"
		ansiColor('xterm') {
			String extraVars="'" + params.extra_vars +"'"
			String cmd="#!/bin/bash -e\n set +x\n" + "ansible-playbook -i ${params.inventory} --extra-vars ${extraVars} ${params.playbook} --connection=local"
			writeFile encoding: 'UTF-8', file: './runAnsible.sh', text: "$cmd"
			sh 'chmod +x ./runAnsible.sh && ./runAnsible.sh'
		}
		//ansiblePlaybook extras: extraVars, inventory: params.inventory, playbook: params.playbook
	}
	stage('cleanup') {
		deleteDir()
	}

}
