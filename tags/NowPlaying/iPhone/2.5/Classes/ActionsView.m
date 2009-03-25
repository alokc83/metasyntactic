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

#import "ActionsView.h"

@interface ActionsView()
@property (assign) id target;
@property (retain) NSArray* selectors;
@property (retain) NSArray* titles;
@property (retain) NSArray* buttons;
@property (retain) UILabel* footer;
@end


@implementation ActionsView

@synthesize target;
@synthesize selectors;
@synthesize titles;
@synthesize buttons;
@synthesize footer;

- (void) dealloc {
    self.target = nil;
    self.selectors = nil;
    self.titles = nil;
    self.buttons = nil;
    self.footer = nil;

    [super dealloc];
}


- (id) initWithTarget:(id) target_
            selectors:(NSArray*) selectors_
               titles:(NSArray*) titles_
               footer:(NSString*) footer_ {
    if (self = [super initWithFrame:CGRectZero]) {
        self.target = target_;
        self.selectors = selectors_;
        self.titles = titles_;
        self.backgroundColor = [UIColor groupTableViewBackgroundColor];

        if (footer_ != nil) {
            self.footer = [[[UILabel alloc] init] autorelease];
            footer.text = footer_;
            footer.opaque = NO;
            footer.backgroundColor = [UIColor clearColor];
        }

        NSMutableArray* array = [NSMutableArray array];
        for (NSString* title in titles) {
            UIButton* button = [UIButton buttonWithType:UIButtonTypeRoundedRect];
            [button setTitle:title forState:UIControlStateNormal];
            [button sizeToFit];

            [button addTarget:self action:@selector(onButtonTapped:) forControlEvents:UIControlEventTouchUpInside];

            [array addObject:button];
            [self addSubview:button];
        }

        self.buttons = array;

        {
            int lastRow = (buttons.count - 1) / 2;

            UIButton* button = [buttons lastObject];
            CGRect frame = button.frame;
            height = (8 + frame.size.height) * (lastRow + 1);
        }
    }

    return self;
}


+ (ActionsView*) viewWithTarget:(id) target
                      selectors:(NSArray*) selectors
                         titles:(NSArray*) titles
                         footer:(NSString*) footer {
    return [[[ActionsView alloc] initWithTarget:(id) target
                                      selectors:selectors
                                         titles:titles
                                         footer:footer] autorelease];
}


+ (ActionsView*) viewWithTarget:(id) target
                      selectors:(NSArray*) selectors
                         titles:(NSArray*) titles {
    return [ActionsView viewWithTarget:target selectors:selectors titles:titles footer:nil];
}


- (void) onButtonTapped:(UIButton*) button {
    SEL selector = [[selectors objectAtIndex:[buttons indexOfObject:button]] pointerValue];
    if ([target respondsToSelector:selector]) {
        [target performSelector:selector];
    }
}


- (CGSize) sizeThatFits:(CGSize) size {
    if (buttons.count == 0) {
        return CGSizeZero;
    }

    double width;
    if (UIDeviceOrientationIsLandscape([UIDevice currentDevice].orientation)) {
        width = [UIScreen mainScreen].bounds.size.height;
    } else {
        width = [UIScreen mainScreen].bounds.size.width;
    }

    return CGSizeMake(width, height);
}


- (CGFloat) height {
    return height;
}


- (void) layoutSubviews {
    [super layoutSubviews];

    BOOL oddNumberOfButtons = ((buttons.count % 2) == 1);

    //int lastRow = (buttons.count - 1) / 2;
    for (int i = 0; i < buttons.count; i++) {
        UIButton* button = [buttons objectAtIndex:i];

        NSInteger column;
        NSInteger row;
        if (oddNumberOfButtons && i != 0) {
            column = (i + 1) % 2;
            row = (i + 1) / 2;
        } else {
            column = i % 2;
            row = i / 2;
        }

        CGRect frame = button.frame;
        frame.origin.x = (column == 0 ? 10 : (self.frame.size.width / 2) + 4);
        frame.origin.y = (8 + frame.size.height) * row + 8;

        if (i == 0 && oddNumberOfButtons) {
            frame.size.width = (self.frame.size.width - 2 * frame.origin.x);
        } else {
            frame.size.width = (self.frame.size.width / 2) - 14;
        }

        button.frame = frame;
    }
}

@end