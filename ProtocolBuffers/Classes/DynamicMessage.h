// Protocol Buffers - Google's data interchange format
// Copyright 2008 Google Inc.
// http://code.google.com/p/protobuf/
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

#import "AbstractMessage.h"

@interface DynamicMessage : AbstractMessage {
@private    
    ProtocolBufferDescriptor* type;
    FieldSet* fields;
    UnknownFieldSet* unknownFields;
    int32_t dm_memoizedSize;
}

@property (retain) ProtocolBufferDescriptor* type;
@property (retain) FieldSet* fields;
@property (retain) UnknownFieldSet* unknownFields;

+ (DynamicMessage*) messageWithType:(ProtocolBufferDescriptor*) type fields:(FieldSet*) fields unknownFields:(UnknownFieldSet*) unknownFields;
+ (DynamicMessage*) getDefaultInstance:(ProtocolBufferDescriptor*) type;

+ (DynamicMessage_Builder*) builderWithType:(ProtocolBufferDescriptor*) type;


#if 0
+ (DynamicMessage*) messageWithType:(Descriptors_Descriptor*) type;


+ (DynamicMessage*) parseFrom:(Descriptors_Descriptor*) type codedInputStream:(CodedInputStream*) codedInputStream;
+ (DynamicMessage*) parseFrom:(Descriptors_Descriptor*) type codedInputStream:(CodedInputStream*) codedInputStream extensionRegistry:(ExtensionRegistry*) extensionRegistry;
+ (DynamicMessage*) parseFrom:(Descriptors_Descriptor*) type data:(NSData*) data;
+ (DynamicMessage*) parseFrom:(Descriptors_Descriptor*) type data:(NSData*) data extensionRegistry:(ExtensionRegistry*) extensionRegistry;
+ (DynamicMessage*) parseFrom:(Descriptors_Descriptor*) type inputStream:(NSInputStream*) inputStream;
+ (DynamicMessage*) parseFrom:(Descriptors_Descriptor*) type inputStream:(NSInputStream*) inputStream extensionRegistry:(ExtensionRegistry*) extensionRegistry;

+ (DynamicMessage_Builder*) builderWithType:(Descriptors_Descriptor*) type;
+ (DynamicMessage_Builder*) builderWithMessage:(id<Message>) prototype;

- (Descriptors_Descriptor*) descriptorForType;
- (DynamicMessage*) defaultInstanceForType;
- (NSDictionary*) allFields;

- (BOOL) hasField:(FieldDescriptor*) field;
- (id) getField:(FieldDescriptor*) field;

- (int32_t) getRepeatedFieldCount:(FieldDescriptor*) field;
- (id) getRepeatedField:(FieldDescriptor*) field index:(int32_t) index;

- (UnknownFieldSet*) unknownFields;
- (BOOL) isInitialized;

- (void) writeToCodedOutputStream:(CodedOutputStream*) output;

- (int32_t) serializedSize;
- (DynamicMessage_Builder*) newBuilderForType;
#endif

@end