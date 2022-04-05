@Library('sharedLibrary')_

properties([
    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '2',daysToKeepStr: '',numToKeepStr: '2')),  
    parameters([
        string(name: 'branch', defaultValue: 'main ', description: 'commit id'),
        string(name: 'options', defaultValue: '-B -DskipTests clean package', description: 'options')
    ])
])
//params is of java.util.Collections$UnmodifiableMap
mavenBuild(params)
