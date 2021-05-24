import { NativeModules } from 'react-native';

type DatatransType = {
  multiply(a: number, b: number): Promise<number>;
};

const { Datatrans } = NativeModules;

export default Datatrans as DatatransType;
