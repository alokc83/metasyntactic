//
//  XmlDocument.m
//  BoxOffice
//
//  Created by Peter Schmitt on 5/3/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

#import "XmlDocument.h"

@implementation XmlDocument

@synthesize root;
@synthesize version;
@synthesize encoding;

- (id) initWithRoot:(XmlElement*) root_
{
	return [self initWithRoot:root_ version:@"1.0" encoding:@"UTF-8"];
}

- (id) initWithRoot:(XmlElement*) root_ 
			version:(NSString*) version_ 
		   encoding:(NSString*) encoding_
{
	if (self = [super init])
	{
		self.root = root_;
		self.version = version_;
		self.encoding = encoding_;
	}
  return self;
}

- (void) dealloc
{
	self.root = nil;
	self.version = nil;
	self.encoding = nil;
	[super dealloc];
}

@end
