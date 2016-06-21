# Simple Trash Cleaner

Provides a simple way of asynchronously clearing the trashcan.  Exposes a webscript which will allow you to trigger a background task to clean the trash can.

## Requirements

* Alfresco 4.2 or above
* Java 7
* Dynamic Extensions

## Usage

Make a `DELETE` http request to the following URL as Admin: `/alfresco/s/parashift/cleantrashcan`

### Responses

You will get one of two plain text responses:

* `Submitting background deletion`:  This indicates that the background deletion task has been submitted.
* `Awaiting existing deletion process`: This indicates that the background deletion task is still active.

### Example CLI

The following is an example using CURL:

    curl -X DELETE -u admin:admin http://localhost:8080/alfresco/s/parashift/cleantrashcan
