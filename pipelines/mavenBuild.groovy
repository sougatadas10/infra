@Library('sharedLibrary')_
pipeline {
    agent any
    parameters {
        string(name: 'branch', defaultValue: '', description: 'commit id')
    }
  mavenBuild(branch: "main",repo: "https://github.com/devopshint/jenkins-pipeline-example.git",file: "./my-app/pom.xml",args: "-B -DskipTests clean package")
 }     

