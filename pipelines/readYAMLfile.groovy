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
					println ("skipping mysql configuration")
				}
				else {
					flagMysql=true
					mysqlParams=value
				}
			}
		else if (key == "vault") {
			if (value == "false") {
				flagVault=false
				println ("skipping vault configuration")
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
		
	}
	stage('vault ansible configuration') {
	if (flagVault) {
		build job: 'runAnsible', propagate: false, parameters: vaultParams
		}
	
	}

    
}
