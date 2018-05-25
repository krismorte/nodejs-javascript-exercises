const { body,validationResult } = require('express-validator/check');
const { sanitizeBody } = require('express-validator/filter');

var http = require('http');
var Genre = require('../models/genre');
var Book = require('../models/book');
var async = require('async');


// Display list of all Genre.
exports.genre_list = function(req, res,next) {
    http.get('http://localhost:8080/genres/', (resp) => {
        let data = '';
       
        // A chunk of data has been recieved.
        resp.on('data', (chunk) => {
          data += chunk;
        });
       
        // The whole response has been received. Print out the result.
        resp.on('end', () => {
            list_genres=JSON.parse(data)
            res.render('genre_list', { title: 'Genre List', genre_list: list_genres });
        });
       
      }).on("error", (err) => {
        if (err) { return next(err); }
      });
};

// Display detail page for a specific Genre.
exports.genre_detail = function(req, res, next) {

    async.parallel({
        genre: function(callback) {
            Genre.findById(req.params.id)
              .exec(callback);
        },

        genre_books: function(callback) {
          Book.find({ 'genre': req.params.id })
          .exec(callback);
        },

    }, function(err, results) {
        if (err) { return next(err); }
        if (results.genre==null) { // No results.
            var err = new Error('Genre not found');
            err.status = 404;
            return next(err);
        }
        // Successful, so render
        res.render('genre_detail', { title: 'Genre Detail', genre: results.genre, genre_books: results.genre_books } );
    });

};

// Display Genre create form on GET.
exports.genre_create_get = function(req, res, next) {       
    res.render('genre_form', { title: 'Create Genre' });
};

// Handle Genre create on POST.
exports.genre_create_post =  [
   
    // Validate that the name field is not empty.
    body('name', 'Genre name required').isLength({ min: 1 }).trim(),
    
    // Sanitize (trim and escape) the name field.
    sanitizeBody('name').trim().escape(),

    // Process request after validation and sanitization.
    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        // Create a genre object with escaped and trimmed data.
        var genre = new Genre(
          { name: req.body.name }
        );


        if (!errors.isEmpty()) {
            // There are errors. Render the form again with sanitized values/error messages.
            res.render('genre_form', { title: 'Create Genre', genre: genre, errors: errors.array()});
        return;
        }
        else {

            var post = http.request({
                hostname: 'localhost',
                port: 8080,
                path: '/genres',
                method: 'POST', // <--- aqui podes escolher o método
                headers: {
                    'Content-Type': 'application/json',
                }
            },  (resp) => {

                // The whole response has been received. Print out the result.
                resp.on('data', function (body) {
                    console.log('Body: ' + body);
                });
        
                resp.on('end', function (body) {
                    res.redirect('/catalog/genres');
                });                

            });
    
            post.on("error", (err) => {
                console.log('problem with request: ' + err.message);
                if (err) { return next(err); }
            });

            post.write(JSON.stringify(genre));
            post.end();                     

        }
    }
];

// Display Genre delete form on GET.
exports.genre_delete_get = function(req, res) {
    async.parallel({
        genre: function(callback) {
            Genre.findById(req.params.id).exec(callback)
        },
        genres_books: function(callback) {
            Book.find({ 'genre': req.params.id }).exec(callback)
        },
    }, function(err, results) {
        if (err) { return next(err); }
        if (results.genre==null) { // No results.
            res.redirect('/catalog/genres');
        }
        // Successful, so render.
        res.render('genre_delete', { title: 'Delete Genre', genre: results.genre, genres_books: results.genres_books } );
    });

};

// Handle Genre delete on POST.
exports.genre_delete_post = function(req, res) {

    var post = http.request({
        hostname: 'localhost',
        port: 8080,
        path: '/genres/'+req.params.id,
        method: 'DELETE', // <--- aqui podes escolher o método
        headers: {
            'Content-Type': 'application/json',
        }
    },  (resp) => {

        // The whole response has been received. Print out the result.
        resp.on('data', function (body) {
            console.log('Body: ' + body);
        });

        resp.on('end', function (body) {
            res.redirect('/catalog/genres');
        });                

    });

    post.on("error", (err) => {
        console.log('problem with request: ' + err.message);
        if (err) { return next(err); }
    });

    post.end(); 

};

// Display Genre update form on GET.
exports.genre_update_get = function(req, res) {
        // Get book, authors and genres for form.
        async.parallel({
            genre: function(callback) {
                Genre.findById(req.params.id).exec(callback);
            },
            }, function(err, results) {
                if (err) { return next(err); }
                if (results.genre==null) { // No results.
                    var err = new Error('Genre not found');
                    err.status = 404;
                    return next(err);
                }
                // Success.
                res.render('genre_form', { title: 'Update Genre', genre: results.genre });
            });
};

// Handle Genre update on POST.
exports.genre_update_post = [
     
    // Validate that the name field is not empty.
    body('name', 'Genre name required').isLength({ min: 1 }).trim(),
    
    // Sanitize (trim and escape) the name field.
    sanitizeBody('name').trim().escape(),

    // Process request after validation and sanitization.
    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        // Create a genre object with escaped and trimmed data.
        var genre = new Genre(
            { name: req.body.name,
                _id:req.params.id //This is required, or a new ID will be assigned!
            }
        );

        console.log(genre);

        if (!errors.isEmpty()) {
            // There are errors. Render the form again with sanitized values/error messages.
            res.render('genre_form', { title: 'Create Genre', genre: genre, errors: errors.array()});
        return;
        }
        else {          

           /* Genre.findByIdAndUpdate(req.params.id, genre, {}, function (err,thegenre) {
            if (err) { return next(err); }
            // Genre saved. Redirect to genre detail page.
            res.redirect(thegenre.url);
            });*/

            
            var post = http.request({
                hostname: 'localhost',
                port: 8080,
                path: '/genres/'+req.params.id,
                method: 'PUT', // <--- aqui podes escolher o método
                headers: {
                    'Content-Type': 'application/json',
                }
            },  (resp) => {

                // The whole response has been received. Print out the result.
                resp.on('data', function (body) {
                    console.log('Body: ' + body);
                });
        
                resp.on('end', function (body) {
                    res.redirect('/catalog/genres');
                });                

            });
    
            post.on("error", (err) => {
                console.log('problem with request: ' + err.message);
                if (err) { return next(err); }
            });

            post.write(JSON.stringify(genre));
            post.end(); 

        }
    }
];