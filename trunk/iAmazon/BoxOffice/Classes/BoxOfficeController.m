//
//  BoxOfficeController.m
//  BoxOffice
//
//  Created by Cyrus Najmabadi on 4/30/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

#import "BoxOfficeController.h"
#import "Movie.h"
#import "Theater.h"
#import "BoxOfficeAppDelegate.h"
#import "TheaterShowtimesXmlParser.h"
#import "XmlParser.h"

@implementation BoxOfficeController

@synthesize appDelegate;

- (id) initWithAppDelegate:(BoxOfficeAppDelegate*) appDel
{
    if (self = [super init])
    {
        self.appDelegate = appDel;
        
        [self spawnBackgroundThreads];
    }
    
    return self;
}

- (void) dealloc
{
    self.appDelegate = nil;
    [super dealloc];
}

- (BoxOfficeModel*) model
{
    return self.appDelegate.model;
}

- (void) spawnBackgroundThreads
{
    NSLog(@"BoxOfficeController:spawnBackgroundThreads");
    
    [self performSelectorInBackground:@selector(lookupMoviesBackgroundThreadEntryPoint:) withObject:nil];
    [self performSelectorInBackground:@selector(lookupTheatersBackgroundThreadEntryPoint:) withObject:nil];
}

- (void) lookupMoviesBackgroundThreadEntryPoint:(id) anObject
{	
    NSLog(@"BoxOfficeController:lookupMoviesBackgroundThreadEntryPoint");
    NSAutoreleasePool* autoreleasePool= [[NSAutoreleasePool alloc] init];
    
    [self lookupMovies];
    
    [autoreleasePool release];
}

- (void) lookupMovies
{
    NSLog(@"BoxOfficeController:lookupMovies");
    
    NSURL* url = [NSURL URLWithString:@"http://i.rottentomatoes.com/syndication/tab/in_theaters.txt"];
	NSError* httpError = nil;
	NSString* inTheaters = [NSString stringWithContentsOfURL:url encoding:NSASCIIStringEncoding error:&httpError];
    
    if (httpError != nil)
    {
        NSLog(@"Error occurred retrieving movie data %@", httpError);
        return;
    }
    
    NSArray* rows = [inTheaters componentsSeparatedByString:@"\n"];
    NSMutableArray* movies = [[NSMutableArray alloc] init];
    
    // first row are the column headers.  last row is empty.  skip both.
    for (NSInteger i = 1; i < [rows count] - 1; i++)
    {   
		NSArray* columns = [[rows objectAtIndex:i] componentsSeparatedByString:@"\t"];
		Movie* movie = [[Movie alloc] initWithTitle:[columns objectAtIndex:1]
                                               link:[columns objectAtIndex:2]
                                           synopsis:[columns objectAtIndex:8]
                                             rating:[columns objectAtIndex:3]];
        [movies addObject:movie];
    }
    
    [self performSelectorOnMainThread:@selector(setMovies:) withObject:movies waitUntilDone:NO];
}

- (void) setMovies:(NSArray*) movies
{
    NSLog(@"BoxOfficeController:setMovies");
    [self.model setMovies:movies];
    [self.appDelegate.tabBarController.moviesNavigationController refresh];
    //[self.appDelegate.tabBarController.moviesNavigationC reloadData];
}

- (void) lookupTheatersBackgroundThreadEntryPoint:(id) anObject
{	
    NSLog(@"BoxOfficeController:lookupTheatersBackgroundThreadEntryPoint");
    NSAutoreleasePool* autoreleasePool= [[NSAutoreleasePool alloc] init];
    
    [self lookupTheaters];
    
    [autoreleasePool release];
}

- (void) lookupTheaters
{   
    NSLog(@"Looking up theaters");
    NSString *post =
    @"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"    
    "<SOAP-ENV:Envelope "
        "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
        "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
        "xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" "
        "SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" "
        "xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">"
        "<SOAP-ENV:Body>"
            "<GetTheatersAndMovies xmlns=\"http://www.ignyte.com/whatsshowing\">"
                "<zipCode xsi:type=\"xsd:string\">10009</zipCode>"
                "<radius xsi:type=\"xsd:int\">5</radius>"
            "</GetTheatersAndMovies>"
        "</SOAP-ENV:Body>"
    "</SOAP-ENV:Envelope>";
    NSData *postData = [post dataUsingEncoding:NSASCIIStringEncoding allowLossyConversion:YES];
        
    NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
    [request setURL:[NSURL URLWithString:@"http://www.ignyte.com/webservices/ignyte.whatsshowing.webservice/moviefunctions.asmx"]];
    [request setHTTPMethod:@"POST"];
    
    [request setValue:@"text/xml" forHTTPHeaderField:@"Content-Type"];
    [request setValue:@"http://www.ignyte.com/whatsshowing/GetTheatersAndMovies" forHTTPHeaderField:@"Soapaction"];
    [request setValue:@"www.ignyte.com" forHTTPHeaderField:@"Host"];
    
    [request setHTTPBody:postData];    
    
    NSURLResponse* response = nil;
    NSError* error = nil;
    NSData* result =
        [NSURLConnection sendSynchronousRequest:request
                              returningResponse:&response
                                          error:&error];
    if (error != nil || result == nil)
    {
        return;
    }
    
    TheaterShowtimesXmlParser* parser = [[[TheaterShowtimesXmlParser alloc] initWithData:result] autorelease];

    [self setTheaters:parser.theaters];
    
    XmlElement* element = [XmlParser parse:result];
    NSString* string = [element description];
    
    NSLog(@"%@ %@", element, string);
}

- (void) setTheaters:(NSArray*) theaters
{
    if (theaters != nil)
    {
        NSLog(@"BoxOfficeController:setTheaters");
        [self.model setTheaters:theaters];
        [self.appDelegate.tabBarController.theatersNavigationController refresh];
    }
}

@end
