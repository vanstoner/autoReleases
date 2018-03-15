xlr {
  release('AutoRelease-GenerateTasks-1.0.6') {
    variables {
      mapVariable('components') {
        showOnReleaseStart false
        value 'component2':'1.4.2','component3':'1.1.1','component1':'1.0.1'
      }
      booleanVariable('isManual') {
        
      }
    }
    scheduledStartDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2018-02-08T09:00:00+0000')
    tags 'autoRelease'
    scriptUsername 'admin'
    scriptUserPassword '{b64}bc4KjBgfVvxUgRxHVWGNVg=='
    autoStart 'true'
    phases {
      phase('DEV') {
        color '#009CDB'
        tasks {
          userInput('Choose Components') {
            description 'Please enter the required information below.'
            owner 'admin'
            precondition 'releaseVariables[\'isManual\']'
            variables {
              variable 'components'
            }
          }
          script('Add tasks to this release') {
            script 'deployComponents = releaseVariables[\'components\']\n' +
                   'containerTask = taskApi.searchTasksByTitle(\'Deploy\',None, getCurrentRelease().id)\n' +
                   'print (containerTask)\n' +
                   'if containerTask[0].id:\n' +
                   '    print "Found the following task %s" % containerTask[0].id\n' +
                   '    for key, value in deployComponents.iteritems():\n' +
                   '      task = taskApi.newTask("xlrelease.ScriptTask")\n' +
                   '      task.title = "Deploy %s at version %s" % (key,value)\n' +
                   '      task.script = "import time\\ntime.sleep(10)"\n' +
                   '      taskApi.addTask(containerTask[0].id, task)\n' +
                   '    \n' +
                   'print "Done"'
          }
          parallelGroup('Deploy') {
            
          }
        }
      }
    }
  }
}
