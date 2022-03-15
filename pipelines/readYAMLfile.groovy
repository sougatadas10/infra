@Library('sharedLibrary')_
import com.foo.readYML

def fRead=new readYML()
def jobs=[]
def mysqlParams=[],vaultParams=[]
boolean flagMysql,flagVault


node() {
    stage('clone') {
        git branch: 'main', changelog: false, poll: false, url: 'https://github.com/sougatadas10/pipelines.git'
    }
    stage('read') {
        def config=readYaml file: "./envStateFiles/envState.yml"
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
			build job: 'runAnsible', propagate: false, parameters: mysqlParams
		}
		else {
			println ("skipping mysql configuration")
		}
		
	}
	stage('vault ansible configuration') {
	if (flagVault) {
		build job: 'runAnsible', propagate: false, parameters: vaultParams
		}
	else {
		println ("skipping vault configuration")
		}
	}

    
}
