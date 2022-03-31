@Library('sharedLibrary')_

properties([
    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '2',daysToKeepStr: '',numToKeepStr: '2')),  
    parameters([
        string(name: 'branch', defaultValue: ' ', description: 'commit id'),
        string(name: 'repo', defaultValue: ' ', description: 'github repo'),
        string(name: 'file', defaultValue: ' ', description: 'POM file name'),
        string(name: 'options', defaultValue: '-B -DskipTests clean package', description: 'options'),
        
    ])
])
mavenBuild(params,this)
