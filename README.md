# ct-react-native-datatrans

datatrans android and ios integration

## Installation

```sh
npm install ct-react-native-datatrans
```

## Usage

```js
import Datatrans from "ct-react-native-datatrans";

// ...

const result = await Datatrans.transaction('mobileToken');
```
or

```js
const result = await Datatrans.transaction('mobileToken',options);

 options = {
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


callbackshema name is "ctdtsdk"
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.


## Datatrans version 1.5
    If you need to activate datatrans version 1.4 use ct-react-native-datatrans@1.4 or else use latest version to support to datatran version 1.5.1
  

## Note
IOS card payment done. Live payment also tested

## License

MIT
