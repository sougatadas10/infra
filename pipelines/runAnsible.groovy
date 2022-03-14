node() {
	properties([
		buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '2',daysToKeepStr: '',numToKeepStr: '2')),
		parameters([
			string(name: 'flavor',defaultValue: '',description: 'Possible values:standalone,cluster' ),
			string(name: 'notifiers',defaultValue: '',description: 'list of email ids to be notified in case of failure' ),
			string(name: 'inventory',defaultValue: '',description: 'inventory file name to be used by ansible' ),
			string(name: 'playbook',defaultValue: '',description: 'name of ansible playbook' ),
			string(name: 'extra_vars',defaultValue: '',description: 'list of extra variables. To be passed as json' )
		])
	stage('run ansible') {
		String extraVars="'" + params.extra_vars +"'"
		ansiblePlaybook extras: extraVars, inventory: params.inventory, playbook: params.playbook
	}

}
