import { NativeModules } from 'react-native';

type options = {
  aliasPaymentMethods?: [{
    alias?: string,
    ccNumber?: string,
    expiryMonth?: number,
    expiryYear?: number,
    paymentMethods?: string
    cardHolder?:string
  }],
  isTesting: boolean,
  isUseCertificatePinning: boolean,
  appCallbackScheme: string
}
type DatatransType = {
  multiply(a: number, b: number): Promise<number>;
  transaction(mobileToken: string, options?: options): Promise<any>;
};

const { Datatrans } = NativeModules;

export default Datatrans as DatatransType;
