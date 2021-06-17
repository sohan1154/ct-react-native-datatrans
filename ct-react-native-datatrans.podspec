require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "ct-react-native-datatrans"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = package["homepage"]
  s.license      = package["license"]
  s.authors      = package["author"]

  s.platforms    = { :ios => "11.0" }
  s.source       = { :git => "https://github.com/sabashBlaze/ct-react-native-datatrans.git", :tag => "#{s.version}"}

  s.source_files = "ios/**/*.{h,m,mm}"
  
  #s.Pod "ios/dependencies/Datatrans"

  #s.subspec "CTdatatranss" do |ctd|
    #ctd.source =  {:git => "https://github.com/datatrans/ios-sdk"}
    #ctd.source_files = "ios/dependencies/Datatrans"
    #ctd.dependency "Datatrans"
  #end
  s.dependency "React-Core"
  #s.dependency "Datatrans"
 # s.dependency "Datatrans" , {:git => "https://github.com/datatrans/ios-sdk"}
end
