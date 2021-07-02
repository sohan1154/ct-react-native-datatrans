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
  isTesing: boolean,
  isUseCertificatePinning: boolean,
  appCallbackScheme: string
}
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.
## Note
IOS card payment done

## License

MIT
