-- Table for all movies
CREATE TABLE Movie (
    mID NUMBER NOT NULL,
    movieTitle VARCHAR(200) NOT NULL,
    releaseYear INT NOT NULL,
    allCriticsRating FLOAT,
    allCriticsNumReviews INT,
    topCriticsRating FLOAT,
    topCriticsNumReviews INT,
    audienceRating FLOAT,
    audienceNumRatings INT,
    PRIMARY KEY(mID)
);

-- Table for all movie genres
CREATE TABLE Genres(
    mID NUMBER NOT NULL,
    genre VARCHAR(50) NOT NULL,
    PRIMARY KEY(mID, genre),
    FOREIGN KEY(mID) REFERENCES Movie(mID) ON DELETE CASCADE
);

-- Table for all movie countries
CREATE TABLE MovieCountries(
    mID NUMBER NOT NULL,
    movieCountry VARCHAR(50),
    FOREIGN KEY(mID) REFERENCES Movie(mID) ON DELETE CASCADE
);

-- Table for all movie filming locations
CREATE TABLE MovieFilmingLocations(
    mID NUMBER NOT NULL,
    filmingLocation VARCHAR(50),
    FOREIGN KEY(mID) REFERENCES Movie(mID) ON DELETE CASCADE
);

-- Table for tag values
CREATE TABLE TagValues(
    tagID NUMBER NOT NULL,
    tagText VARCHAR(100) NOT NULL,
    PRIMARY KEY(tagID)
);

-- Table for movie tags
CREATE TABLE MovieTags(
    mID NUMBER NOT NULL,
    tagID NUMBER NOT NULL,
    tagWeight NUMBER NOT NULL,
    PRIMARY KEY(mID, tagID),
    FOREIGN KEY(mID) REFERENCES Movie(mID) ON DELETE CASCADE,
    FOREIGN KEY(tagID) REFERENCES TagValues(tagID) ON DELETE CASCADE
);

-- Create indexes for searching
CREATE INDEX genreIndex ON Genres(genre);
CREATE INDEX countryIndex ON MovieCountries(movieCountry);
CREATE INDEX locationsIndex ON MovieFilmingLocations(filmingLocation);
CREATE INDEX criticsRatingsIndex ON Movie(allCriticsRating);
CREATE INDEX criticsNumReviewsIndex ON Movie(allCriticsNumReviews);
CREATE INDEX movieYearIndex ON Movie(releaseYear);
--CREATE INDEX tagIDIndex ON MovieTags(tagID);
--CREATE INDEX mIDIndex ON MovieTags(mID);
--CREATE INDEX locationsIndex2 ON MovieFilmingLocations(mID);

-- "C:\Users\Michael\IdeaProjects\hw3\sql\createdb.sql"