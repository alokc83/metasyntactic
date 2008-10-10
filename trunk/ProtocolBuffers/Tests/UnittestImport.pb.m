//Copyright 2008 Cyrus Najmabadi
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

#import "UnittestImport.pb.h"

@implementation UnittestImportProtoRoot
static PBFileDescriptor* descriptor = nil;
static PBDescriptor* internal_static_protobuf_unittest_import_ImportMessage_descriptor = nil;
static PBFieldAccessorTable* internal_static_protobuf_unittest_import_ImportMessage_fieldAccessorTable = nil;
+ (PBDescriptor*) internal_static_protobuf_unittest_import_ImportMessage_descriptor {
  return internal_static_protobuf_unittest_import_ImportMessage_descriptor;
}
+ (PBFieldAccessorTable*) internal_static_protobuf_unittest_import_ImportMessage_fieldAccessorTable {
  return internal_static_protobuf_unittest_import_ImportMessage_fieldAccessorTable;
}
+ (void) initialize {
  if (self == [UnittestImportProtoRoot class]) {
    descriptor = [[UnittestImportProtoRoot buildDescriptor] retain];
    internal_static_protobuf_unittest_import_ImportMessage_descriptor = [[[self descriptor].messageTypes objectAtIndex:0] retain];
    {
      NSArray* fieldNames = [NSArray arrayWithObjects:@"D", nil];
      internal_static_protobuf_unittest_import_ImportMessage_fieldAccessorTable = 
        [[PBFieldAccessorTable tableWithDescriptor:internal_static_protobuf_unittest_import_ImportMessage_descriptor
                                        fieldNames:fieldNames
                                      messageClass:[ImportMessage class]
                                      builderClass:[ImportMessage_Builder class]] retain];
    }
  }
}
+ (PBFileDescriptor*) descriptor {
  return descriptor;
}
+ (PBFileDescriptor*) buildDescriptor {
  static uint8_t descriptorData[] = {
    10,37,103,111,111,103,108,101,47,112,114,111,116,111,98,117,102,47,117,
    110,105,116,116,101,115,116,95,105,109,112,111,114,116,46,112,114,111,116,
    111,18,24,112,114,111,116,111,98,117,102,95,117,110,105,116,116,101,115,
    116,95,105,109,112,111,114,116,34,26,10,13,73,109,112,111,114,116,77,101,
    115,115,97,103,101,18,9,10,1,100,24,1,32,1,40,5,42,60,10,10,73,109,112,
    111,114,116,69,110,117,109,18,14,10,10,73,77,80,79,82,84,95,70,79,79,16,
    7,18,14,10,10,73,77,80,79,82,84,95,66,65,82,16,8,18,14,10,10,73,77,80,79,
    82,84,95,66,65,90,16,9,66,28,10,24,99,111,109,46,103,111,111,103,108,101,
    46,112,114,111,116,111,98,117,102,46,116,101,115,116,72,1,
  };
  NSArray* dependencies = [NSArray arrayWithObjects:nil];
  
  NSData* data = [NSData dataWithBytes:descriptorData length:185];
  PBFileDescriptorProto* proto = [PBFileDescriptorProto parseFromData:data];
  return [PBFileDescriptor buildFrom:proto dependencies:dependencies];
}
@end

@interface ImportEnum ()
  @property int32_t index;
  @property int32_t value;
@end

@implementation ImportEnum
@synthesize index;
@synthesize value;
static ImportEnum* ImportEnum_IMPORT_FOO = nil;
static ImportEnum* ImportEnum_IMPORT_BAR = nil;
static ImportEnum* ImportEnum_IMPORT_BAZ = nil;
- (id) initWithIndex:(int32_t) index_ value:(int32_t) value_ {
  if (self = [super init]) {
    self.index = index_;
    self.value = value_;
  }
  return self;
}
+ (ImportEnum*) newWithIndex:(int32_t) index value:(int32_t) value {
  return [[[ImportEnum alloc] initWithIndex:index value:value] autorelease];
}
+ (void) initialize {
  if (self == [ImportEnum class]) {
    ImportEnum_IMPORT_FOO = [[ImportEnum newWithIndex:0 value:7] retain];
    ImportEnum_IMPORT_BAR = [[ImportEnum newWithIndex:1 value:8] retain];
    ImportEnum_IMPORT_BAZ = [[ImportEnum newWithIndex:2 value:9] retain];
  }
}
+ (ImportEnum*) IMPORT_FOO { return ImportEnum_IMPORT_FOO; }
+ (ImportEnum*) IMPORT_BAR { return ImportEnum_IMPORT_BAR; }
+ (ImportEnum*) IMPORT_BAZ { return ImportEnum_IMPORT_BAZ; }
- (int32_t) number { return value; }
+ (ImportEnum*) valueOf:(int32_t) value {
  switch (value) {
    case 7: return [ImportEnum IMPORT_FOO];
    case 8: return [ImportEnum IMPORT_BAR];
    case 9: return [ImportEnum IMPORT_BAZ];
    default: return nil;
  }
}
- (PBEnumValueDescriptor*) valueDescriptor {
  return [[ImportEnum descriptor].values objectAtIndex:index];
}
- (PBEnumDescriptor*) descriptor {
  return [ImportEnum descriptor];
}
+ (PBEnumDescriptor*) descriptor {
  return [[UnittestImportProtoRoot descriptor].enumTypes objectAtIndex:0];
}
+ (ImportEnum*) valueOfDescriptor:(PBEnumValueDescriptor*) desc {
  if (desc.type != [ImportEnum descriptor]) {
    @throw [NSException exceptionWithName:@"" reason:@"" userInfo:nil];
  }
  ImportEnum* VALUES[] = {
    [ImportEnum IMPORT_FOO],
    [ImportEnum IMPORT_BAR],
    [ImportEnum IMPORT_BAZ],
  };
  return VALUES[desc.index];
}
@end

@interface ImportMessage ()
@property BOOL hasD;
@property int32_t d;
@end

@implementation ImportMessage

@synthesize hasD;
@synthesize d;
- (void) dealloc {
  self.hasD = NO;
  self.d = 0;
  [super dealloc];
}
- (id) init {
  if (self = [super init]) {
    self.d = 0;
  }
  return self;
}
static ImportMessage* defaultImportMessageInstance = nil;
+ (void) initialize {
  if (self == [ImportMessage class]) {
    defaultImportMessageInstance = [[ImportMessage alloc] init];
  }
}
+ (ImportMessage*) defaultInstance {
  return defaultImportMessageInstance;
}
- (ImportMessage*) defaultInstance {
  return defaultImportMessageInstance;
}
- (PBDescriptor*) descriptor {
  return [ImportMessage descriptor];
}
+ (PBDescriptor*) descriptor {
  return [UnittestImportProtoRoot internal_static_protobuf_unittest_import_ImportMessage_descriptor];
}
- (PBFieldAccessorTable*) internalGetFieldAccessorTable {
  return [UnittestImportProtoRoot internal_static_protobuf_unittest_import_ImportMessage_fieldAccessorTable];
}
- (BOOL) isInitialized {
  return true;
}
- (void) writeToCodedOutputStream:(PBCodedOutputStream*) output {
  if (hasD) {
    [output writeInt32:1 value:self.d];
  }
  [self.unknownFields writeToCodedOutputStream:output];
}
- (int32_t) serializedSize {
  int32_t size = memoizedSerializedSize;
  if (size != -1) return size;

  size = 0;
  if (hasD) {
    size += computeInt32Size(1, self.d);
  }
  size += self.unknownFields.serializedSize;
  memoizedSerializedSize = size;
  return size;
}
+ (ImportMessage*) parseFromData:(NSData*) data {
  return (ImportMessage*)[[[ImportMessage_Builder builder] mergeFromData:data] build];
}
+ (ImportMessage*) parseFromData:(NSData*) data extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  return (ImportMessage*)[[[ImportMessage_Builder builder] mergeFromData:data extensionRegistry:extensionRegistry] build];
}
+ (ImportMessage*) parseFromInputStream:(NSInputStream*) input {
  return (ImportMessage*)[[[ImportMessage_Builder builder] mergeFromInputStream:input] build];
}
+ (ImportMessage*) parseFromInputStream:(NSInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  return (ImportMessage*)[[[ImportMessage_Builder builder] mergeFromInputStream:input extensionRegistry:extensionRegistry] build];
}
+ (ImportMessage*) parseFromCodedInputStream:(PBCodedInputStream*) input {
  return (ImportMessage*)[[[ImportMessage_Builder builder] mergeFromCodedInputStream:input] build];
}
+ (ImportMessage*) parseFromCodedInputStream:(PBCodedInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  return (ImportMessage*)[[[ImportMessage_Builder builder] mergeFromCodedInputStream:input extensionRegistry:extensionRegistry] build];
}
- (ImportMessage_Builder*) createBuilder {
  return [ImportMessage_Builder builder];
}
@end

@implementation ImportMessage_Builder
@synthesize result;
- (void) dealloc {
  self.result = nil;
  [super dealloc];
}
- (id) init {
  if (self = [super init]) {
    self.result = [[[ImportMessage alloc] init] autorelease];
  }
  return self;
}
+ (ImportMessage_Builder*) builder {
  return [[[ImportMessage_Builder alloc] init] autorelease];
}
+ (ImportMessage_Builder*) builderWithPrototype:(ImportMessage*) prototype {
  return [[ImportMessage_Builder builder] mergeFromImportMessage:prototype];
}
- (ImportMessage*) internalGetResult {
  return result;
}
- (ImportMessage_Builder*) clear {
  self.result = [[[ImportMessage alloc] init] autorelease];
  return self;
}
- (ImportMessage_Builder*) clone {
  return [ImportMessage_Builder builderWithPrototype:result];
}
- (PBDescriptor*) descriptor {
  return [ImportMessage descriptor];
}
- (ImportMessage*) defaultInstance {
  return [ImportMessage defaultInstance];
}
- (ImportMessage*) build {
  if (!self.isInitialized) {
    @throw [NSException exceptionWithName:@"UninitializedMessage" reason:@"" userInfo:nil];
  }
  return [self buildPartial];
}
- (ImportMessage*) buildPartial {
  ImportMessage* returnMe = [[result retain] autorelease];
  self.result = nil;
  return returnMe;
}
- (ImportMessage_Builder*) mergeFromMessage:(id<PBMessage>) other {
  id o = other;
  if ([o isKindOfClass:[ImportMessage class]]) {
    return [self mergeFromImportMessage:o];
  } else {
    [super mergeFromMessage:other];
    return self;
  }
}
- (ImportMessage_Builder*) mergeFromImportMessage:(ImportMessage*) other {
  if (other == [ImportMessage defaultInstance]) return self;
  if (other.hasD) {
    [self setD:other.d];
  }
  [self mergeUnknownFields:other.unknownFields];
  return self;
}
- (ImportMessage_Builder*) mergeFromCodedInputStream:(PBCodedInputStream*) input {
  return [self mergeFromCodedInputStream:input extensionRegistry:[PBExtensionRegistry emptyRegistry]];
}
- (ImportMessage_Builder*) mergeFromCodedInputStream:(PBCodedInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  PBUnknownFieldSet_Builder* unknownFields = [PBUnknownFieldSet_Builder builderWithUnknownFields:self.unknownFields];
  while (true) {
    int32_t tag = [input readTag];
    switch (tag) {
      case 0:
        [self setUnknownFields:[unknownFields build]];
        return self;
      default: {
        if (![self parseUnknownField:input unknownFields:unknownFields extensionRegistry:extensionRegistry tag:tag]) {
          [self setUnknownFields:[unknownFields build]];
          return self;
        }
        break;
      }
      case 8: {
        [self setD:[input readInt32]];
        break;
      }
    }
  }
}
- (BOOL) hasD {
  return result.hasD;
}
- (int32_t) d {
  return result.d;
}
- (ImportMessage_Builder*) setD:(int32_t) value {
  result.hasD = YES;
  result.d = value;
  return self;
}
- (ImportMessage_Builder*) clearD {
  result.hasD = NO;
  result.d = 0;
  return self;
}
@end