#import "Datatrans.h"
#import <Datatrans/Datatrans.h>
#import <React/RCTRootView.h>

@implementation SimpleViewDelegate

- (void)setCallback:(RCTPromiseResolveBlock)resolve {
    self.resolve = resolve;
}

- (void)dismissView {
    UIViewController *topViewController = [UIApplication sharedApplication].keyWindow.rootViewController;

    while (topViewController.presentedViewController) {
        topViewController = topViewController.presentedViewController;
    }
    
    [topViewController dismissViewControllerAnimated:YES completion:nil];
}

- (void)userDidCancelSimple:(DTTransaction * _Nonnull)scanViewController  API_AVAILABLE(ios(11.2)){
    [self dismissView];
    self.resolve(@{ @"action" : @"canceled" });
}

/*- (void)userDidScanCardSimple:(DTTransaction * _Nonnull)scanViewController creditCard:(CreditCard * _Nonnull)creditCard  API_AVAILABLE(ios(11.2)){
    [self dismissView];
   
}*/
-(void)transactionDidCancel:(DTTransaction *)transaction{
    NSLog( @"Cancelled");
    [self dismissView];
    self.resolve(@{
        @"action" : @"Cancel",
        @"data": @{
        }
    });
}
-(void)transactionDidFinish:(DTTransaction * )transaction result:(DTTransactionSuccess *)result{
    NSLog( @"Finish");
    [self dismissView];
    NSString *transactionId = result.transactionId;
        DTPaymentMethodToken *paymentMethodToken = result.paymentMethodToken;
   // DTPaymentMethodType *paymentMethodType = result.paymentMethodType;
    self.resolve(@{
        @"action" : @"Finish",
        @"data": @{
            @"transactionId": transactionId?: [NSNull null],
            @"paymentMethodToken": paymentMethodToken?: [NSNull null],
           // @"paymentMethodType": paymentMethodType?:[NSNull null]
        }
    });
                 
    
    
}
-(void)transactionDidFail:(DTTransaction *)transaction error:(DTTransactionError *)error{
    NSLog( @"Fail");
    [self dismissView];
    NSString *transactionId = error.transactionId;
        NSNumber *paymentMethodType = error.paymentMethodType;
    
    self.resolve(@{
        @"action" : @"Error",
        @"data": @{
            @"transactionId": transactionId?: [NSNull null],
            @"paymentMethodType": paymentMethodType?: [NSNull null],
            // @"paymentMethodType": result.paymentMethodType
        }
    });
}

@end
@implementation Datatrans

- (id)init {
    if(self = [super init]) {
        self.simpleViewDelegate = [[SimpleViewDelegate alloc] init];
    }
    return self;
}

RCT_EXPORT_MODULE()

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
                 mobileTokenWithA:(nonnull NSString*)mobileToken withB:(nonnull NSDictionary*)options
                 //aliasPaymentMethods
                   
  withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)
{
    
    [self.simpleViewDelegate setCallback:resolve];
    dispatch_async(dispatch_get_main_queue(), ^(void) {
        
      //  UIWindow *window = [UIApplication sharedApplication].keyWindow;
      //  UIViewController *rootViewController = window.rootViewController;
        UIViewController *rootViewController = [UIApplication sharedApplication].keyWindow.rootViewController;
     
        while (rootViewController.presentedViewController) {
            rootViewController = rootViewController.presentedViewController;
          }
        DTPaymentMethodToken* dpmt = [[DTPaymentMethodToken alloc] initWithType:DTPaymentMethodTypeMasterCard token:@"AAABelu1dqPssdexyrAAAXMP6X2QAJRV"];
        //dpm.
      
        ///DTCard* dc=[[DTCard alloc] init]
        DTTransaction* transactions;
      //
        //NSArray* mt=@[dpmt];
        if([options isKindOfClass:[NSDictionary class]]){
            
       
        if([options objectForKey:@"aliasPaymentMethods"])
        {
            DTCardExpiryDate* dtCardExpiryDate;
            dtCardExpiryDate.month=12;
            dtCardExpiryDate.year=21;
            DTCardToken* dct=[[DTCardToken alloc] initWithType:DTPaymentMethodTypeMasterCard token:@"AAABegpC1VrssdexyrAAAYOwm9FmAKtz" cardExpiryDate:dtCardExpiryDate maskedCardNumber:@"520000xxxxxx0007" cardholder:@""];
            
            transactions = [[DTTransaction alloc] initWithMobileToken:mobileToken paymentMethodTokens:@[dct]];
        }
        else{
            transactions = [[DTTransaction alloc] initWithMobileToken:mobileToken];
            
        }
        // aliasPaymentMethods:aliasPaymentMethods
        transactions.delegate = self.simpleViewDelegate;//(id<DTTransactionDelegate>) self;
        BOOL testing=[[options valueForKey:@"isTesting"] boolValue]?:YES;
        
        BOOL useCertificatePinning=[[options valueForKey:@"isUseCertificatePinning"] boolValue]?:YES;
        
        transactions.options.appCallbackScheme = options[@"appCallbackScheme"]?:@"ct-datatrans";
        transactions.options.testing =testing;
transactions.options.useCertificatePinning = useCertificatePinning;
      
        [transactions startWithPresentingController:rootViewController];
        }
        else{
            reject(@"invalid option",@"Invalid Option",NULL);
        }
        
    });
    }

@end
