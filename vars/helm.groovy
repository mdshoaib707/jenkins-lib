def upgrade(Map config) {
  if (config.chart == null) {
    throw new Exception("The required parameter 'chart' was not set.")
  }
  
  if (config.name == null) {
    throw new Exception("The required parameter 'name' was not set.")
  }
  
  config.bin = config.bin == null ? 'helm3' : config.bin
  
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
  catch (Exception error) {
  }
  finally {
  }
  
}
