// Generated by the protocol buffer compiler.  DO NOT EDIT!

#import "UnittestOptimizeFor.pb.h"

@implementation UnittestOptimizeForProtoRoot
static PBFileDescriptor* descriptor = nil;
static PBDescriptor* internal_static_protobuf_unittest_TestOptimizedForSize_descriptor = nil;
static PBFieldAccessorTable* internal_static_protobuf_unittest_TestOptimizedForSize_fieldAccessorTable = nil;
+ (PBDescriptor*) internal_static_protobuf_unittest_TestOptimizedForSize_descriptor {
  return internal_static_protobuf_unittest_TestOptimizedForSize_descriptor;
}
+ (PBFieldAccessorTable*) internal_static_protobuf_unittest_TestOptimizedForSize_fieldAccessorTable {
  return internal_static_protobuf_unittest_TestOptimizedForSize_fieldAccessorTable;
}
+ (void) initialize {
  if (self == [UnittestOptimizeForProtoRoot class]) {
    descriptor = [[UnittestOptimizeForProtoRoot buildDescriptor] retain];
    internal_static_protobuf_unittest_TestOptimizedForSize_descriptor = [[[self descriptor].messageTypes objectAtIndex:0] retain];
    {
      NSArray* fieldNames = [NSArray arrayWithObjects:@"I", @"Msg", nil];
      internal_static_protobuf_unittest_TestOptimizedForSize_fieldAccessorTable = 
        [[PBFieldAccessorTable tableWithDescriptor:internal_static_protobuf_unittest_TestOptimizedForSize_descriptor
                                        fieldNames:fieldNames
                                      messageClass:[TestOptimizedForSize class]
                                      builderClass:[TestOptimizedForSize_Builder class]] retain];
    }
  }
}
+ (PBFileDescriptor*) descriptor {
  return descriptor;
}
+ (PBFileDescriptor*) buildDescriptor {
  static uint8_t descriptorData[] = {
    10,43,103,111,111,103,108,101,47,112,114,111,116,111,98,117,102,47,117,
    110,105,116,116,101,115,116,95,111,112,116,105,109,105,122,101,95,102,111,
    114,46,112,114,111,116,111,18,17,112,114,111,116,111,98,117,102,95,117,
    110,105,116,116,101,115,116,26,30,103,111,111,103,108,101,47,112,114,111,
    116,111,98,117,102,47,117,110,105,116,116,101,115,116,46,112,114,111,116,
    111,34,158,1,10,20,84,101,115,116,79,112,116,105,109,105,122,101,100,70,
    111,114,83,105,122,101,18,9,10,1,105,24,1,32,1,40,5,18,46,10,3,109,115,
    103,24,19,32,1,40,11,50,33,46,112,114,111,116,111,98,117,102,95,117,110,
    105,116,116,101,115,116,46,70,111,114,101,105,103,110,77,101,115,115,97,
    103,101,42,9,8,232,7,16,128,128,128,128,2,50,64,10,14,116,101,115,116,95,
    101,120,116,101,110,115,105,111,110,18,39,46,112,114,111,116,111,98,117,
    102,95,117,110,105,116,116,101,115,116,46,84,101,115,116,79,112,116,105,
    109,105,122,101,100,70,111,114,83,105,122,101,24,210,9,32,1,40,5,66,2,72,
    2,
  };
  NSArray* dependencies = [NSArray arrayWithObjects:[UnittestProtoRoot descriptor], nil];
  
  NSData* data = [NSData dataWithBytes:descriptorData length:261];
  PBFileDescriptorProto* proto = [PBFileDescriptorProto parseFromData:data];
  return [PBFileDescriptor buildFrom:proto dependencies:dependencies];
}
@end

@interface TestOptimizedForSize ()
@property BOOL hasI;
@property int32_t i;
@property BOOL hasMsg;
@property (retain) ForeignMessage* msg;
@end

@implementation TestOptimizedForSize

@synthesize hasI;
@synthesize i;
@synthesize hasMsg;
@synthesize msg;
- (void) dealloc {
  self.hasI = NO;
  self.i = 0;
  self.hasMsg = NO;
  self.msg = nil;
  [super dealloc];
}
- (id) init {
  if (self = [super init]) {
    self.i = 0;
  }
  return self;
}
static PBGeneratedExtension* TestOptimizedForSize_testExtension = nil;
+ (PBGeneratedExtension*) testExtension {
  return TestOptimizedForSize_testExtension;
}
static TestOptimizedForSize* defaultTestOptimizedForSizeInstance = nil;
+ (void) initialize {
  if (self == [TestOptimizedForSize class]) {
    defaultTestOptimizedForSizeInstance = [[TestOptimizedForSize alloc] init];
     TestOptimizedForSize_testExtension = [[PBGeneratedExtension extensionWithDescriptor:[[self descriptor].extensions objectAtIndex:0]
                                                       type:[NSNumber class]] retain];
  }
}
+ (TestOptimizedForSize*) defaultInstance {
  return defaultTestOptimizedForSizeInstance;
}
- (TestOptimizedForSize*) defaultInstanceForType {
  return defaultTestOptimizedForSizeInstance;
}
- (PBDescriptor*) descriptorForType {
  return [TestOptimizedForSize descriptor];
}
+ (PBDescriptor*) descriptor {
  return [UnittestOptimizeForProtoRoot internal_static_protobuf_unittest_TestOptimizedForSize_descriptor];
}
- (PBFieldAccessorTable*) internalGetFieldAccessorTable {
  return [UnittestOptimizeForProtoRoot internal_static_protobuf_unittest_TestOptimizedForSize_fieldAccessorTable];
}
- (BOOL) isInitialized {
  if (!self.extensionsAreInitialized) return false;
  return true;
}
- (void) writeToCodedOutputStream:(PBCodedOutputStream*) output {
  PBExtensionWriter* extensionWriter = [PBExtensionWriter writerWithExtensions:self.extensions];
  if (hasI) {
    [output writeInt32:1 value:self.i];
  }
  if (self.hasMsg) {
    [output writeMessage:19 value:self.msg];
  }
  [extensionWriter writeUntil:536870912 output:output];
  [self.unknownFields writeToCodedOutputStream:output];
}
- (int32_t) serializedSize {
  int32_t size = memoizedSerializedSize;
  if (size != -1) return size;

  size = 0;
  if (hasI) {
    size += computeInt32Size(1, self.i);
  }
  if (self.hasMsg) {
    size += computeMessageSize(19, self.msg);
  }
  size += [self extensionsSerializedSize];
  size += self.unknownFields.serializedSize;
  memoizedSerializedSize = size;
  return size;
}
+ (TestOptimizedForSize*) parseFromData:(NSData*) data {
  return (TestOptimizedForSize*)[[[TestOptimizedForSize newBuilder] mergeFromData:data] build];
}
+ (TestOptimizedForSize*) parseFromData:(NSData*) data extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  return (TestOptimizedForSize*)[[[TestOptimizedForSize newBuilder] mergeFromData:data extensionRegistry:extensionRegistry] build];
}
+ (TestOptimizedForSize*) parseFromInputStream:(NSInputStream*) input {
  return (TestOptimizedForSize*)[[[TestOptimizedForSize newBuilder] mergeFromInputStream:input] build];
}
+ (TestOptimizedForSize*) parseFromInputStream:(NSInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  return (TestOptimizedForSize*)[[[TestOptimizedForSize newBuilder] mergeFromInputStream:input extensionRegistry:extensionRegistry] build];
}
+ (TestOptimizedForSize*) parseFromCodedInputStream:(PBCodedInputStream*) input {
  return (TestOptimizedForSize*)[[[TestOptimizedForSize newBuilder] mergeFromCodedInputStream:input] build];
}
+ (TestOptimizedForSize*) parseFromCodedInputStream:(PBCodedInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  return (TestOptimizedForSize*)[[[TestOptimizedForSize newBuilder] mergeFromCodedInputStream:input extensionRegistry:extensionRegistry] build];
}
+ (TestOptimizedForSize_Builder*) newBuilder { return [[[TestOptimizedForSize_Builder alloc] init] autorelease]; }
- (TestOptimizedForSize_Builder*) newBuilderForType { return [TestOptimizedForSize newBuilder]; }
+ (TestOptimizedForSize_Builder*) newBuilderWithTestOptimizedForSize:(TestOptimizedForSize*) prototype {
  return [[TestOptimizedForSize newBuilder] mergeFromTestOptimizedForSize:prototype];
}
@end

@implementation TestOptimizedForSize_Builder
@synthesize result;
- (void) dealloc {
  self.result = nil;
  [super dealloc];
}
- (id) init {
  if (self = [super init]) {
    self.result = [[[TestOptimizedForSize alloc] init] autorelease];
  }
  return self;
}
- (TestOptimizedForSize*) internalGetResult {
  return result;
}
- (TestOptimizedForSize_Builder*) clear {
  self.result = [[[TestOptimizedForSize alloc] init] autorelease];
  return self;
}
- (TestOptimizedForSize_Builder*) clone {
  return (TestOptimizedForSize_Builder*)[[[[TestOptimizedForSize_Builder alloc] init] autorelease] mergeFromTestOptimizedForSize:result];
}
- (PBDescriptor*) descriptorForType {
  return [TestOptimizedForSize descriptor];
}
- (TestOptimizedForSize*) defaultInstanceForType {
  return [TestOptimizedForSize defaultInstance];
}
- (TestOptimizedForSize*) build {
  if (!self.isInitialized) {
    @throw [NSException exceptionWithName:@"UninitializedMessage" reason:@"" userInfo:nil];
  }
  return [self buildPartial];
}
- (TestOptimizedForSize*) buildPartial {
  TestOptimizedForSize* returnMe = [[result retain] autorelease];
  self.result = nil;
  return returnMe;
}
- (TestOptimizedForSize_Builder*) mergeFromMessage:(id<PBMessage>) other {
  id o = other;
  if ([o isKindOfClass:[TestOptimizedForSize class]]) {
    return [self mergeFromTestOptimizedForSize:o];
  } else {
    [super mergeFromMessage:other];
    return self;
  }
}
- (TestOptimizedForSize_Builder*) mergeFromTestOptimizedForSize:(TestOptimizedForSize*) other {
  if (other == [TestOptimizedForSize defaultInstance]) return self;
  if (other.hasI) {
    [self setI:other.i];
  }
  if (other.hasMsg) {
    [self mergeMsg:other.msg];
  }
  [self mergeUnknownFields:other.unknownFields];
  return self;
}
- (TestOptimizedForSize_Builder*) mergeFromCodedInputStream:(PBCodedInputStream*) input {
  return [self mergeFromCodedInputStream:input extensionRegistry:[PBExtensionRegistry emptyRegistry]];
}
- (TestOptimizedForSize_Builder*) mergeFromCodedInputStream:(PBCodedInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  PBUnknownFieldSet_Builder* unknownFields = [PBUnknownFieldSet newBuilder:self.unknownFields];
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
        [self setI:[input readInt32]];
        break;
      }
      case 154: {
        ForeignMessage_Builder* subBuilder = [ForeignMessage newBuilder];
        if (self.hasMsg) {
          [subBuilder mergeFromForeignMessage:self.msg];
        }
        [input readMessage:subBuilder extensionRegistry:extensionRegistry];
        [self setMsg:[subBuilder buildPartial]];
        break;
      }
    }
  }
}
- (BOOL) hasI {
  return result.hasI;
}
- (int32_t) i {
  return result.i;
}
- (TestOptimizedForSize_Builder*) setI:(int32_t) value {
  result.hasI = YES;
  result.i = value;
  return self;
}
- (TestOptimizedForSize_Builder*) clearI {
  result.hasI = NO;
  result.i = 0;
  return self;
}
- (BOOL) hasMsg {
  return result.hasMsg;
}
- (ForeignMessage*) msg {
  return result.msg;
}
- (TestOptimizedForSize_Builder*) setMsg:(ForeignMessage*) value {
  result.hasMsg = YES;
  result.msg = value;
  return self;
}
- (TestOptimizedForSize_Builder*) setMsgBuilder:(ForeignMessage_Builder*) builderForValue {
  return [self setMsg:[builderForValue build]];
}
- (TestOptimizedForSize_Builder*) mergeMsg:(ForeignMessage*) value {
  if (result.hasMsg &&
      result.msg != [ForeignMessage defaultInstance]) {
    result.msg =
      [[[ForeignMessage newBuilderWithForeignMessage:result.msg] mergeFromForeignMessage:value] buildPartial];
  } else {
    result.msg = value;
  }
  result.hasMsg = YES;
  return self;
}
- (TestOptimizedForSize_Builder*) clearMsg {
  result.hasMsg = NO;
  result.msg = [ForeignMessage defaultInstance];
  return self;
}
@end

