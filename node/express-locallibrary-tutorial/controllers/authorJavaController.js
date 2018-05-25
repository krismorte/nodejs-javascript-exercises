const { body, validationResult } = require('express-validator/check');
const { sanitizeBody } = require('express-validator/filter');

var http = require('http');
var Author = require('../models/author');
var async = require('async');
var Book = require('../models/book');

// Display list of all Authors.
exports.author_list = function (req, res) {

        http.get('http://localhost:8080/authors/', (resp) => {
        let data = '';
       
        // A chunk of data has been recieved.
        resp.on('data', (chunk) => {
          data += chunk;
        });
       
        // The whole response has been received. Print out the result.
        resp.on('end', () => {
            list_authors=JSON.parse(data)
            res.render('author_list', { title: 'Author List', author_list: list_authors });
        });
       
      }).on("error", (err) => {
        if (err) { return next(err); }
      });
};

// Display detail page for a specific Author.
exports.author_detail = function (req, res, next) {

    async.parallel({
        author: function (callback) {
            Author.findById(req.params.id)
                .exec(callback)
        },
        authors_books: function (callback) {
            Book.find({ 'author': req.params.id }, 'title summary')
                .exec(callback)
        },
    }, function (err, results) {
        if (err) { return next(err); } // Error in API usage.
        if (results.author == null) { // No results.
            var err = new Error('Author not found');
            err.status = 404;
            return next(err);
        }
        // Successful, so render.
        res.render('author_detail', { title: 'Author Detail', author: results.author, author_books: results.authors_books });
    });

};

// Display Author create form on GET.
exports.author_create_get = function (req, res) {
    res.render('author_form', { title: 'Create Author' });
};

// Handle Author create on POST.
exports.author_create_post = [

    // Validate fields.
    body('first_name').isLength({ min: 1 }).trim().withMessage('First name must be specified.')
        /*.isDecimal().withMessage('First name has non-alphanumeric characters.')*/,
    body('family_name').isLength({ min: 1 }).trim().withMessage('Family name must be specified.')
        /*.isDecimal().withMessage('Family name has non-alphanumeric characters.')*/,
    body('date_of_birth', 'Invalid date of birth').optional({ checkFalsy: true }).isISO8601(),
    body('date_of_death', 'Invalid date of death').optional({ checkFalsy: true }).isISO8601(),

    // Sanitize fields.
    sanitizeBody('first_name').trim().escape(),
    sanitizeBody('family_name').trim().escape(),
    sanitizeBody('date_of_birth').toDate(),
    sanitizeBody('date_of_death').toDate(),

    // Process request after validation and sanitization.
    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        if (!errors.isEmpty()) {
            // There are errors. Render form again with sanitized values/errors messages.
            res.render('author_form', { title: 'Create Author', author: req.body, errors: errors.array() });
            return;
        }
        else {
            // Data from form is valid.

            // Create an Author object with escaped and trimmed data.
            var author = new Author(
                {
                    first_name: req.body.first_name,
                    family_name: req.body.family_name,
                    date_of_birth: req.body.date_of_birth,
                    date_of_death: req.body.date_of_death
                });


                var post = http.request({
                    hostname: 'localhost',
                    port: 8080,
                    path: '/authors',
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
                        res.redirect('/catalog/authors');
                    });                
    
                });
        
                post.on("error", (err) => {
                    console.log('problem with request: ' + err.message);
                    if (err) { return next(err); }
                });
    
                post.write(JSON.stringify(author));
                post.end(); 

        }
    }
];

// Display Author delete form on GET.
exports.author_delete_get = function (req, res, next) {

    async.parallel({
        author: function (callback) {
            Author.findById(req.params.id).exec(callback)
        },
        authors_books: function (callback) {
            Book.find({ 'author': req.params.id }).exec(callback)
        },
    }, function (err, results) {
        if (err) { return next(err); }
        if (results.author == null) { // No results.
            res.redirect('/catalog/authors');
        }
        // Successful, so render.
        res.render('author_delete', { title: 'Delete Author', author: results.author, author_books: results.authors_books });
    });

};

// Handle Author delete on POST.
exports.author_delete_post = function (req, res, next) {

    async.parallel({
        author: function (callback) {
            Author.findById(req.body.authorid).exec(callback)
        },
        authors_books: function (callback) {
            Book.find({ 'author': req.body.authorid }).exec(callback)
        },
    }, function (err, results) {
        if (err) { return next(err); }
        // Success
        if (results.authors_books.length > 0) {
            // Author has books. Render in same way as for GET route.
            res.render('author_delete', { title: 'Delete Author', author: results.author, author_books: results.authors_books });
            return;
        }
        else {
            // Author has no books. Delete object and redirect to the list of authors.
            Author.findByIdAndRemove(req.body.authorid, function deleteAuthor(err) {
                if (err) { return next(err); }
                // Success - go to author list
                res.redirect('/catalog/authors')
            })
        }
    });
};

// Display Author update form on GET.
exports.author_update_get = function (req, res) {
    // Get book, authors and genres for form.
    async.parallel({
        author: function (callback) {
            Author.findById(req.params.id).exec(callback);
        },
    }, function (err, results) {
        if (err) { return next(err); }
        if (results.author == null) { // No results.
            var err = new Error('Author not found');
            err.status = 404;
            return next(err);
        }
        // Success.
        res.render('author_form', { title: 'Update Author', author: results.author });
    });
};

// Handle Author update on POST.
exports.author_update_post = [

    // Validate fields.
    body('first_name').isLength({ min: 1 }).trim().withMessage('First name must be specified.')
        /*.isDecimal().withMessage('First name has non-alphanumeric characters.')*/,
    body('family_name').isLength({ min: 1 }).trim().withMessage('Family name must be specified.')
        /*.isDecimal().withMessage('Family name has non-alphanumeric characters.')*/,
    body('date_of_birth', 'Invalid date of birth').optional({ checkFalsy: true }).isISO8601(),
    body('date_of_death', 'Invalid date of death').optional({ checkFalsy: true }).isISO8601(),

    // Sanitize fields.
    sanitizeBody('first_name').trim().escape(),
    sanitizeBody('family_name').trim().escape(),
    sanitizeBody('date_of_birth').toDate(),
    sanitizeBody('date_of_death').toDate(),

    // Process request after validation and sanitization.
    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        if (!errors.isEmpty()) {
            // There are errors. Render form again with sanitized values/errors messages.
            res.render('author_form', { title: 'Create Author', author: req.body, errors: errors.array() });
            return;
        }
        else {
            // Data from form is valid.

            // Create an Author object with escaped and trimmed data.
            var author = new Author(
                {
                    first_name: req.body.first_name,
                    family_name: req.body.family_name,
                    date_of_birth: req.body.date_of_birth,
                    date_of_death: req.body.date_of_death,
                    _id: req.params.id //This is required, or a new ID will be assigned!
                });
            Author.findByIdAndUpdate(req.params.id, author, {}, function (err, theauthor) {
                if (err) { return next(err); }
                // Successful - redirect to new author record.
                res.redirect(theauthor.url);
            });
        }
    }


]