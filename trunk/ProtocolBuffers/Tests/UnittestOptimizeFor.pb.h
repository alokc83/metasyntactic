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

#import <ProtocolBuffers/ProtocolBuffers.h>

#import "Unittest.pb.h"

@class PBDescriptor;
@class PBEnumDescriptor;
@class PBEnumValueDescriptor;
@class PBFieldAccessorTable;
@class PBFileDescriptor;
@class PBGeneratedMessage_Builder;
@class BarRequest;
@class BarRequest_Builder;
@class BarResponse;
@class BarResponse_Builder;
@class FooRequest;
@class FooRequest_Builder;
@class FooResponse;
@class FooResponse_Builder;
@class ForeignEnum;
@class ForeignMessage;
@class ForeignMessage_Builder;
@class ImportEnum;
@class ImportMessage;
@class ImportMessage_Builder;
@class OptionalGroup_extension;
@class OptionalGroup_extension_Builder;
@class RepeatedGroup_extension;
@class RepeatedGroup_extension_Builder;
@class TestAllExtensions;
@class TestAllExtensions_Builder;
@class TestAllTypes;
@class TestAllTypes_Builder;
@class TestAllTypes_NestedEnum;
@class TestAllTypes_NestedMessage;
@class TestAllTypes_NestedMessage_Builder;
@class TestAllTypes_OptionalGroup;
@class TestAllTypes_OptionalGroup_Builder;
@class TestAllTypes_RepeatedGroup;
@class TestAllTypes_RepeatedGroup_Builder;
@class TestCamelCaseFieldNames;
@class TestCamelCaseFieldNames_Builder;
@class TestDupFieldNumber;
@class TestDupFieldNumber_Bar;
@class TestDupFieldNumber_Bar_Builder;
@class TestDupFieldNumber_Builder;
@class TestDupFieldNumber_Foo;
@class TestDupFieldNumber_Foo_Builder;
@class TestEmptyMessage;
@class TestEmptyMessageWithExtensions;
@class TestEmptyMessageWithExtensions_Builder;
@class TestEmptyMessage_Builder;
@class TestEnumWithDupValue;
@class TestExtremeDefaultValues;
@class TestExtremeDefaultValues_Builder;
@class TestFieldOrderings;
@class TestFieldOrderings_Builder;
@class TestForeignNested;
@class TestForeignNested_Builder;
@class TestMutualRecursionA;
@class TestMutualRecursionA_Builder;
@class TestMutualRecursionB;
@class TestMutualRecursionB_Builder;
@class TestNestedMessageHasBits;
@class TestNestedMessageHasBits_Builder;
@class TestNestedMessageHasBits_NestedMessage;
@class TestNestedMessageHasBits_NestedMessage_Builder;
@class TestOptimizedForSize;
@class TestOptimizedForSize_Builder;
@class TestReallyLargeTagNumber;
@class TestReallyLargeTagNumber_Builder;
@class TestRecursiveMessage;
@class TestRecursiveMessage_Builder;
@class TestRequired;
@class TestRequiredForeign;
@class TestRequiredForeign_Builder;
@class TestRequired_Builder;
@class TestService;
@class TestSparseEnum;

@interface UnittestOptimizeForProtoRoot : NSObject {
}
+ (PBFileDescriptor*) descriptor;
+ (PBFileDescriptor*) buildDescriptor;
@end

@interface TestOptimizedForSize : PBExtendableMessage {
  BOOL hasI;
  int32_t i;
  BOOL hasMsg;
  ForeignMessage* msg;
}
@property (readonly) BOOL hasI;
@property (readonly) int32_t i;
@property (readonly) BOOL hasMsg;
@property (retain, readonly) ForeignMessage* msg;

+ (PBDescriptor*) descriptor;
- (PBDescriptor*) descriptor;
+ (TestOptimizedForSize*) defaultInstance;
- (TestOptimizedForSize*) defaultInstance;
- (PBFieldAccessorTable*) internalGetFieldAccessorTable;

+ (PBGeneratedExtension*) testExtension;
- (BOOL) isInitialized;
- (void) writeToCodedOutputStream:(PBCodedOutputStream*) output;
- (TestOptimizedForSize_Builder*) createBuilder;

+ (TestOptimizedForSize*) parseFromData:(NSData*) data;
+ (TestOptimizedForSize*) parseFromData:(NSData*) data extensionRegistry:(PBExtensionRegistry*) extensionRegistry;
+ (TestOptimizedForSize*) parseFromInputStream:(NSInputStream*) input;
+ (TestOptimizedForSize*) parseFromInputStream:(NSInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry;
+ (TestOptimizedForSize*) parseFromCodedInputStream:(PBCodedInputStream*) input;
+ (TestOptimizedForSize*) parseFromCodedInputStream:(PBCodedInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry;
@end

@interface TestOptimizedForSize_Builder : PBExtendableBuilder {
 @private
  TestOptimizedForSize* result;
}
@property (retain) TestOptimizedForSize* result;

+ (TestOptimizedForSize_Builder*) builder;
+ (TestOptimizedForSize_Builder*) builderWithPrototype:(TestOptimizedForSize*) prototype;

- (PBDescriptor*) descriptor;
- (TestOptimizedForSize*) defaultInstance;

- (TestOptimizedForSize_Builder*) clear;
- (TestOptimizedForSize_Builder*) clone;

- (TestOptimizedForSize*) build;
- (TestOptimizedForSize*) buildPartial;

- (TestOptimizedForSize_Builder*) mergeFromMessage:(id<PBMessage>) other;
- (TestOptimizedForSize_Builder*) mergeFromTestOptimizedForSize:(TestOptimizedForSize*) other;
- (TestOptimizedForSize_Builder*) mergeFromCodedInputStream:(PBCodedInputStream*) input;
- (TestOptimizedForSize_Builder*) mergeFromCodedInputStream:(PBCodedInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry;

- (BOOL) hasI;
- (int32_t) i;
- (TestOptimizedForSize_Builder*) setI:(int32_t) value;
- (TestOptimizedForSize_Builder*) clearI;

- (BOOL) hasMsg;
- (ForeignMessage*) msg;
- (TestOptimizedForSize_Builder*) setMsg:(ForeignMessage*) value;
- (TestOptimizedForSize_Builder*) setMsgBuilder:(ForeignMessage_Builder*) builderForValue;
- (TestOptimizedForSize_Builder*) mergeMsg:(ForeignMessage*) value;
- (TestOptimizedForSize_Builder*) clearMsg;
@end