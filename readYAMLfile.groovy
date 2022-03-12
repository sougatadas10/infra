@Library('mylib')_
import com.foo.readYML

def fRead=new readYML()
def jobs=[]

node() {
    stage('clone') {
        git branch: 'main', changelog: false, poll: false, url: 'https://github.com/sougatadas10/environmentStateFiles.git'
    }
    stage('read') {
        def config=readYaml file: "./envState.yml"
        jobs=fRead.parse(this,config)
        
        jobs.each {
            key,value -> println ("key: "+key+ " "+ "value: "+ value)    
        }        
        
    }
    
}
