@Library('sharedLibrary')_
import com.foo.mvnBuild

def build = new mvnBuild(this)

node(){
    stage('clone') {
		
		gitCheckout(branch: 'main',url: "https://github.com/devopshint/jenkins-pipeline-example.git")
	}
	stage('build') {
	    build.mvn("${workspace}/my-app/pom.xml","-B -DskipTests clean package")
	}
}
