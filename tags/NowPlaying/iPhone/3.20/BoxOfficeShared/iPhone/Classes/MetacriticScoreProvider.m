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

#import "MetacriticScoreProvider.h"

#import "Application.h"
#import "Score.h"

@implementation MetacriticScoreProvider

+ (MetacriticScoreProvider*) provider {
  return [[[MetacriticScoreProvider alloc] init] autorelease];
}


- (NSString*) providerName {
  return @"Metacritic";
}


- (NSString*) lookupServerHash {
  NSString* address = [NSString stringWithFormat:@"http://%@.appspot.com/LookupMovieScores%@?q=metacritic&hash=true",
                       [Application apiHost], [Application apiVersion]];
  NSString* value = [NetworkUtilities stringWithContentsOfAddress:address pause:NO];
  return value;
}


- (NSMutableDictionary*) lookupServerScores {
  NSString* address = [NSString stringWithFormat:@"http://%@.appspot.com/LookupMovieScores%@?q=metacritic",
                       [Application apiHost], [Application apiVersion]];
  XmlElement* resultElement = [NetworkUtilities xmlWithContentsOfAddress:address pause:NO];

  if (resultElement != nil) {
    NSMutableDictionary* ratings = [NSMutableDictionary dictionary];

    for (XmlElement* movieElement in resultElement.children) {
      NSString* title =    [movieElement attributeValue:@"title"];
      NSString* link =     [movieElement attributeValue:@"link"];
      NSString* synopsis = [movieElement attributeValue:@"synopsis"];
      NSString* score =    [movieElement attributeValue:@"score"];

      if ([score isEqual:@"xx"]) {
        score = @"-1";
      }

      Score* extraInfo = [Score scoreWithTitle:title
                                      synopsis:synopsis
                                         score:score
                                      provider:@"metacritic"
                                    identifier:link];

      [ratings setObject:extraInfo forKey:extraInfo.canonicalTitle];
    }

    return ratings;
  }

  return nil;
}

@end