@Library('sharedLibrary')_
import com.foo.readYML

def fRead=new readYML()
def jobs=[],mysqlParams=[],vaultParams=[]
def jobMysql,jobVault
boolean flagMysql,flagVault


node() {
	properties([
		
		parameters([
			string(name: 'file_name',defaultValue: '',description: 'name of file' ),
			gitParameter(branch: '',
				defaultValue: 'master',
				description: 'commit id to check out',
				name: 'BRANCH')
				
		])
	])
	stage('clone') {
		git branch: params.BRANCH, changelog: false, poll: false, url: 'https://github.com/sougatadas10/pipelines.git'
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
