# Simple Trash Cleaner

Provides a simple way of asynchronously clearing the trashcan in Alfresco.

Sometimes your trash can in Alfresco gets too big and it's hard to manage it from share.  This will blast the trash can away and remove all files from it.

It will also submit this in the background so you don't have to wait around for it to end.

Exposes a webscript which will allow you to trigger a background task to clean the trash can.

## Requirements

* Alfresco 4.2 or above
* Java 7
* Dynamic Extensions

## Compiling

* Install the [Alfresco Amp Plugin](https://bitbucket.org/parashift/alfresco-amp-plugin)
* Run `gradle amp` in this directory

## Installation

You can either:
* Install amp as per usual
* Use `gradle installBundle`
* Use `gradle jar` and deploy it via the Dynamic Extensions console

## Usage

Make a `DELETE` http request to the following URL as Admin: `/alfresco/s/parashift/cleantrashcan`

### Responses

You will get one of two plain text responses:

* `Submitting background deletion`:  This indicates that the background deletion task has been submitted.
* `Awaiting existing deletion process`: This indicates that the background deletion task is still active.

### Example CLI

The following is an example using CURL:

    curl -X DELETE -u admin:admin http://localhost:8080/alfresco/s/parashift/cleantrashcan
