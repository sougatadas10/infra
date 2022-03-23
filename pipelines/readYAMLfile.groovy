@Library('sharedLibrary')_
import com.foo.readYML

def fRead=new readYML()
def jobs=[],mysqlParams=[],vaultParams=[]
def jobMysql,jobVault
boolean flagMysql,flagVault


node() {
	properties([
		buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '2',daysToKeepStr: '',numToKeepStr: '2')),
		parameters([
			string(name: 'file_name',defaultValue: '',description: 'environment file' ),
			string(name: 'commit_id',defaultValue: '',description: 'commit id' )
			])
		])
	
	stage('clone') {
		/**checkout([$class: 'GitSCM', 
			branches: [[name: "${params.commit_id}"]], extensions: [], 
			userRemoteConfigs: [[url: 'https://github.com/sougatadas10/infra.git']]])**/
		gitCheckout(branch: params.commit_id,url: "https://github.com/sougatadas10/infra.git")
	}
	stage('read') {
		def config=readYaml file: params.file_name
		jobs=fRead.parse(this,config)

		jobs.each {
			key,value -> println ("key: "+key+ " "+ "value: "+ value)
			if (key == "mysql") {
				if (value == "false") {
					flagMysql=false
				}
				else {
					flagMysql=true
					mysqlParams=value
				}
			}
			else if (key == "vault") {
				if (value == "false") {
					flagVault=false
				}
				else {
					flagVault=true
					vaultParams=value
				}
			}
			else {
				error('invalid option')
			}

		}

	}
	stage('mysql ansible configuration') {
		if (flagMysql) {
			jobMysql=build job: 'runAnsible', propagate: false, parameters: mysqlParams
		}
		else {
			println ("skipping mysql configuration")
		}

	}
	stage('vault ansible configuration') {
		if (flagVault) {
			jobVault=build job: 'runAnsible', propagate: false, parameters: vaultParams
		}
		else {
			println ("skipping vault configuration")
		}
	}
	stage('set result'){
		if (flagMysql)
			if (jobMysql.getResult()=='SUCCESS')
				println('Ansible configuration for mysql is successful')
			else {
				println('Ansible configuration for mysql has failed')
				currentbuild.result='FAILURE'
			}

		if (flagVault)
			if (jobVault.getResult()=='SUCCESS')
				println('Ansible configuration for vault is successful')
			else {
				println('Ansible configuration for vault has failed')
				currentbuild.result='FAILURE'
			}

	}


}
