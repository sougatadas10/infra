@Library('mylib')_
import com.foo.readYML

def fRead=new readYML()
def jobs=[],keys=[]

node() {
    stage('clone') {
        git branch: 'main', changelog: false, poll: false, url: 'https://github.com/sougatadas10/environmentStateFiles.git'
    }
    stage('read') {
        def config=readYaml file: "./envState.yml"
        jobs=fRead.parse(this,config)
        
        keys=jobs.get(jobs.size()-1)
        
        for (int i=0; i <keys.size(); ++i){
            println keys.get(i)
            println jobs.get(i)
        }
        
       
        
        
    }
    
}
