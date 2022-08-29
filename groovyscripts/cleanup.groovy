import hudson.model.*
  
def deleted = []
//def oneDayAgo = new Date() - 1
def oneDayAgo = new Date()
jenkins.model.Jenkins.instance.nodes.each { hudson.model.Node node ->
  node.workspaceRoot.listDirectories().each { hudson.FilePath path ->
    def pathName = path.getRemote()
    if (path.name.startsWith(".")) {
      println "Skipping internal dir $node.displayName:$pathName"
    } else {
        def lastModified = new Date(path.lastModified())
        println "lastModified: $lastModified" 
      if (lastModified <= oneDayAgo) {
        println "Deleting workspace at $node.displayName:$pathName (last modified $lastModified)"
        path.deleteRecursive()
        deleted << "$node.displayName:$pathName"
      } else {
        println "Skipping workspace at $node.displayName:$pathName (last modified $lastModified)"
      }
    }
  }
}
"Deleted workspaces: \n\t" + deleted.sort().join("\n\t")