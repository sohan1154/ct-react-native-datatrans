#import <React/RCTBridgeModule.h>
#import <Datatrans/Datatrans.h>

@interface SimpleViewDelegate : NSObject <DTTransactionDelegate>

@property RCTPromiseResolveBlock resolve;

- (void)setCallback:(RCTPromiseResolveBlock)resolve;
- (void)dismissView;

@end

@interface Datatrans : NSObject <RCTBridgeModule>
@property (nonatomic) SimpleViewDelegate * simpleViewDelegate;
@end
