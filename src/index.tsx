import { NativeModules } from 'react-native';

type DatatransType = {
  multiply(a: number, b: number): Promise<number>;
  transaction(mobileToken: string, aliasPaymentMethods:string): Promise<any>;
};

const { Datatrans } = NativeModules;

export default Datatrans as DatatransType;
