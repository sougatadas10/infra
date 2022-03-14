@Library('sharedLibrary')_
import com.foo.readYML

def fRead=new readYML()
def jobs=[]
def ansibleParams=[]
String flagDeployAnsible

node() {
    //stage('clone') {
    //    git branch: 'main', changelog: false, poll: false, url: 'https://github.com/sougatadas10/environmentStateFiles.git'
    //}
    stage('read') {
        def config=readYaml file: "./envStateFiles/envState.yml"
        jobs=fRead.parse(this,config)
        
        jobs.each {
            key,value -> println ("key: "+key+ " "+ "value: "+ value) 
			if (key == "mysql") {
				if (value == "false") {
					flagDeployAnsible=false
					println ("skipping mysql configuration")
				}
				else {
					flagDeployAnsible=true
					ansibleParams=value
				}
			}
        }        
        
    }
	stage('trigger ansible') {
		
		if (flagDeployAnsible) {
			build job: 'runAnsible', propagate: true, parameters: ansibleParams
		}
		
	}
    
}
