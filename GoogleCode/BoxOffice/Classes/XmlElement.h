// Copyright (c) 2008, Cyrus Najmabadi
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
//   Redistributions of source code must retain the above copyright notice, this
//   list of conditions and the following disclaimer.
//
//   Redistributions in binary form must reproduce the above copyright notice,
//   this list of conditions and the following disclaimer in the documentation
//   and/or other materials provided with the distribution.
//
//   Neither the name 'Cyrus Najmabadi' nor the names of its contributors may be
//   used to endorse or promote products derived from this software without
//   specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

@interface XmlElement : NSObject {
    NSString* name;
    NSDictionary* attributes;
    NSArray* children;
    // the text between the start and end tags (not counting the child elements)
    NSString* text;
}

@property (copy) NSString* name;
@property (retain) NSDictionary* attributes;
@property (retain) NSArray* children;
@property (copy) NSString* text;

+ (id) elementWithName:(NSString*) name;

+ (id) elementWithName:(NSString*) name
            attributes:(NSDictionary*) attributes;

+ (id) elementWithName:(NSString*) name
              children:(NSArray*) children;

+ (id) elementWithName:(NSString*) name
                  text:(NSString*) text;

+ (id) elementWithName:(NSString*) name
            attributes:(NSDictionary*) attributes
              children:(NSArray*) children;

+ (id) elementWithName:(NSString*) name
            attributes:(NSDictionary*) attributes
                  text:(NSString*) text;

+ (id) elementWithName:(NSString*) name
              children:(NSArray*) children
                  text:(NSString*) text;

+ (id) elementWithName:(NSString*) name
            attributes:(NSDictionary*) attributes
              children:(NSArray*) children
                  text:(NSString*) text;

- (id) initWithName:(NSString*) name
         attributes:(NSDictionary*) attributes
           children:(NSArray*) children
               text:(NSString*) text;

- (NSString*) description;

- (NSDictionary*) dictionary;
+ (XmlElement*) elementFromDictionary:(NSDictionary*) dictionary;

- (XmlElement*) element:(NSString*) name;
- (NSArray*) elements:(NSString*) name;

- (XmlElement*) elementAtIndex:(NSInteger) index;

- (NSString*) attributeValue:(NSString*) key;

@end
