def repo = 'https://github.com/sougatadas10/infra.git'

pipelineJob('connectDatabase') {
  
/** Scheduling job
  triggers {
    scm('H/5 * * * *')
  } **/
//  description("Pipeline for connectDatabase")

  definition {
    cpsScm {
      scm {
        git {
          remote { url(repo) }
          //branches('master', '**/feature*') Can be used if we have pipelines in different branch
          branches('main')
          scriptPath('pipelines/connectDatabase.groovy')
          extensions { }  // required as otherwise it may try to tag the repo, which you may not want
        }

        // the single line below also works, but it
        // only covers the 'master' branch and may not give you
        // enough control.
        // git(repo, 'master', { node -> node / 'extensions' << '' } )
      }
    }
  }
}    

pipelineJob('mavenBuild') {
//  description("Pipeline for mavenBuild")
  defination {
    cpsScm {
      scm {
        git {
          remote { url(repo) }
          //branches('master', '**/feature*') Can be used if we have pipelines in different branch
          branches('main')
          scriptPath('pipelines/mavenBuild.groovy')
          extensions { }  // required as otherwise it may try to tag the repo, which you may not want
        }
      }
    }
  }
}    

pipelineJob('multibranch-readYAML') {
//  description("Pipeline for multibranch-readYAML")
  defination {  
    cpsScm {
      scm {
        git {
          remote { url(repo) }
          //branches('master', '**/feature*') Can be used if we have pipelines in different branch
          branches('main')
          scriptPath('pipelines/multibranch-readYAML.groovy')
          extensions { }  // required as otherwise it may try to tag the repo, which you may not want
        }
      }
    }
  }
}  

pipelineJob('readYAMLfile') {
//  description("Pipeline for readYAMLfile")
  defination {  
    cpsScm {
      scm {
        git {
          remote { url(repo) }
          //branches('master', '**/feature*') Can be used if we have pipelines in different branch
          branches('main')
          scriptPath('pipelines/readYAMLfile.groovy')
          extensions { }  // required as otherwise it may try to tag the repo, which you may not want
        }
      }
    }
  }
}  

pipelineJob('runAnsible') {
//  description("Pipeline for runAnsible")
  defination {  
    cpsScm {
      scm {
        git {
          remote { url(repo) }
          //branches('master', '**/feature*') Can be used if we have pipelines in different branch
          branches('main')
          scriptPath('pipelines/runAnsible.groovy')
          extensions { }  // required as otherwise it may try to tag the repo, which you may not want
        }
      }
    }
  }
}
