// Copyright 2008 Cyrus Najmabadi
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

#import "MetasyntacticSharedApplication.h"

#import "MetasyntacticSharedApplicationDelegate.h"

@implementation MetasyntacticSharedApplication

static id<MetasyntacticSharedApplicationDelegate> delegate = nil;

+ (void) setSharedApplicationDelegate:(id<MetasyntacticSharedApplicationDelegate>) delegate_ {
  delegate = delegate_;
}


+ (NSString*) localizedString:(NSString*) key {
  return [delegate localizedString:key];
}


+ (void) saveNavigationStack:(UINavigationController*) controller {
  [delegate saveNavigationStack:controller];
}


+ (BOOL) notificationsEnabled {
  return [delegate notificationsEnabled];
}


+ (void) minorRefresh {
  [delegate minorRefresh];
}


+ (void) majorRefresh {
  [delegate majorRefresh];
}


+ (void) majorRefresh:(BOOL) force {
  [delegate majorRefresh:force];
}

@end