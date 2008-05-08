//
//  TheaterLocationCache.h
//  BoxOffice
//
//  Created by Cyrus Najmabadi on 5/8/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "Location.h"

@interface AddressLocationCache : NSObject {
}

+ (AddressLocationCache*) cache;

- (void) updateAddresses:(NSArray*) addresses;
- (void) updateZipcode:(NSString*) zipcode;

- (Location*) locationForAddress:(NSString*) address;
- (Location*) locationForZipcode:(NSString*) zipcode;

@end
