def upgrade(Map config) {
  if (config.chart == null) {
    throw new Exception("The required parameter 'chart' was not set.")
  }
  
  if (config.name == null) {
    throw new Exception("The required parameter 'name' was not set.")
  }
  
  config.bin = config.bin == null ? 'helm' : config.bin
  
  // upgrade the helm
  try {
    cmd = "${config.bin} upgrade"
    
    if (config.values != null) {
      if (!(config.values instanceof String[]) && !(config.set instanceof List<String>)) {
        throw new Exception('The values parameter must be an array of strings.')
      }
      
      config.values.each() { value ->
        if (!(fileExists(value))) {
            throw new Exception("Value overrides file ${value} does not exist!")
        }
        cmd += " -f ${value}"
      }
    }
    
    if (config.set != null) {
      if (!(config.set instanceof String[]) && !(config.set instanceof List<String>)) {
        throw new Exception('The set parameter must be an array/list of strings.')
      }
      config.set.each() { kv ->
        cmd += " --set ${kv}"
      }
    }
    
    if (config.reuseValues == true) {
      cmd += " --reuse-values"
    }
    if (config.context != null) {
      cmd += " --kube-context ${config.context}"
    }
    if (config.namespace != null) {
      cmd += " --namespace ${config.namespace}"
    }
    if (config.wait == true) {
      cmd += " --wait"
    }
    if (config.verify == true) {
      cmd += " --verify"
    }
    if (config.debug == true) {
      cmd += " --debug"
    }
    if (config.recreatePods == true) {
      cmd += " --recreate-pods"
    }
    if (config.force == true) {
      cmd += " --force"
    }
    if (config.timeout) {
      cmd += " --timeout ${config.timeout}"
    }
    
    cmd += " --install ${config.name} ${config.chart}"
    if (config.debug == true) {
      echo "[INFO] helm.upgrade - runing helm cli: ${cmd}"
    }
    
    sh cmd
    
  }
  catch (Exception error) {
    echo '[ERROR] helm.upgrade - Failure using helm upgrade.'
    throw error
  }
  
  echo '[INFO] helm.upgrade - Helm upgrade executed successfully.'
  
}
