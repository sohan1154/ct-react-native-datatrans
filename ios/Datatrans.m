#import "Datatrans.h"
#import <Datatrans/Datatrans.h>

@implementation CTdatatrans
RCT_EXPORT_MODULE()
//@interface RCT_EXTERN_MODULE(Navigation, NSObject)



// Example method
// See // https://reactnative.dev/docs/native-modules-ios
RCT_REMAP_METHOD(multiply,
                 multiplyWithA:(nonnull NSNumber*)a withB:(nonnull NSNumber*)b
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)
{
  NSNumber *result = @([a floatValue] * [b floatValue]);

  resolve(result);
}
RCT_REMAP_METHOD(transaction,
                 mobileTokenWithA:(nonnull NSString*)mobileToken withB:(nonnull NSString*)aliastoken
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)
{
    
    DTTransaction* transactions = [[DTTransaction alloc] initWithMobileToken:mobileToken];
    // aliasPaymentMethods:aliasPaymentMethods
    transactions.delegate = self;
transactions.options.appCallbackScheme = @"cashless";
transactions.options.testing = YES;
transactions.options.useCertificatePinning = YES;
[transactions startWithPresentingController:self];
    resolve(transactions);
}


@end
