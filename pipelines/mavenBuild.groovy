@Library('sharedLibrary')_


mavenBuild(repo: "https://github.com/devopshint/jenkins-pipeline-example.git",file: "./my-app/pom.xml",args: "-B -DskipTests clean package",body)
      

