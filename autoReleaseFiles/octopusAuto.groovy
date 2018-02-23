// Exported from:        http://Robs-Pro.local:5516/#/templates/Folder43fbe2f960104407ac113711e83ba31b-Release553d70dc510b4382b96e039dab4399dc/releasefile
// XL Release version:   7.5.0
// Date created:         Fri Feb 23 13:14:47 GMT 2018

xlr {
  release('AutoRelease-GenerateTasks-R12345') {
    variables {
      mapVariable('components') {
        showOnReleaseStart false
        value 'component2':'1.3.1','component3':'1.1.1','component1':'1.0.1'
      }
      booleanVariable('isManual') {
        
      }
    }
    scheduledStartDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2018-02-08T09:00:00+0000')
    autoStart 'true'
    tags 'autoRelease'
    scriptUsername 'admin'
    scriptUserPassword '{b64}/fmIVfOrU2FWrBY55tD9eg=='
    // no DSL renderer found for property 'riskProfile' of type 'xlrelease.Release'
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
                   'containerTask = taskApi.searchTasksByTitle(\'OctopusDeploy\',None, getCurrentRelease().id)\n' +
                   'print (containerTask)\n' +
                   'if containerTask[0].id:\n' +
                   '    print "Found the following task %s" % containerTask[0].id\n' +
                   '    for key, value in deployComponents.iteritems():\n' +
                   '      task = taskApi.newTask("octopus.DeployRelease")\n' +
                   '      task.title = "Deploy %s at version %s" % (key,value)\n' +
                   '      task.pythonScript.environment = "Dev"\n' +
                   '      taskApi.addTask(containerTask[0].id, task)\n' +
                   '    \n' +
                   'print "Done"'
          }
          parallelGroup('OctopusDeploy') {
            
          }
        }
      }
    }
  }
}
