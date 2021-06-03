import * as React from 'react';

import { StyleSheet, View, Text,TouchableOpacity } from 'react-native';
import Datatrans from 'react-native-datatrans';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();

  React.useEffect(() => {
    Datatrans.multiply(3, 7).then(setResult);
   
  }, []);
const checkTans=()=>{
  console.log('checktrans')
  try{
  Datatrans.transaction('asdfas2312312', '21312313').then(res=>
    console.log('res',res)
  ).catch(err=>console.log('err',err));
  }catch(err){
    console.error(err);
  }
}
  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
      <TouchableOpacity onPress={checkTans}><Text>Click to check</Text></TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
