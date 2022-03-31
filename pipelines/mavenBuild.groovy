@Library('sharedLibrary')_


mavenBuild(branch: "main",repo: "https://github.com/devopshint/jenkins-pipeline-example.git",file: "./my-app/pom.xml",args: "-B -DskipTests clean package")
      

