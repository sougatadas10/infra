@Library('sharedLibrary')_

properties([
		buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '2',daysToKeepStr: '',numToKeepStr: '2')),
		parameters([
			string(name: 'commit_id',defaultValue: '',description: 'commit id' )
			])
	])

mavenBuild(branch: "main",repo: "https://github.com/devopshint/jenkins-pipeline-example.git",file: "./my-app/pom.xml",args: "-B -DskipTests clean package")
      

