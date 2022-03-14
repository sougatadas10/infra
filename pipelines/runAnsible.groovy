node() {
  stage('run ansible') {
    ansiblePlaybook extras: '{a:b}', inventory: 'inventory/local.yml', playbook: 'test.yml'
  }

}
