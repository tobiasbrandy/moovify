
-- Populate Post Categories --
INSERT INTO POST_CATEGORY (creation_date, name)
VALUES (now(), 'watchlist');

INSERT INTO POST_CATEGORY (creation_date, name)
VALUES (now(), 'critique');

INSERT INTO POST_CATEGORY (creation_date, name)
VALUES (now(), 'news');

INSERT INTO POST_CATEGORY (creation_date, name)
VALUES (now(), 'debate');

-- Populate User Roles --
INSERT INTO ROLES (role)
VALUES ('USER');

INSERT INTO ROLES (role)
VALUES ('ADMIN');

INSERT INTO ROLES (role)
VALUES ('NOT_VALIDATED');

-- Populate Genre --

INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(28, 'action');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(12, 'adventure');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(16, 'animation');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(35, 'comedy');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(80, 'crime');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(99, 'documentary');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(18, 'drama');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(10751, 'family');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(14, 'fantasy');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(36, 'history');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(27, 'horror');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(10402, 'music');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(9648, 'mystery');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(10749, 'romance');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(878, 'scienceFiction');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(10770, 'tvMovie');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(53, 'thriller');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(10752, 'war');
INSERT INTO  movie_categories(tmdb_category_id, name) VALUES(37, 'western');


INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2015-06-17', 'Minions', 'Minions', 211672, 'tt2293640', 'en', 'Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.', 547.488298, 91, 6.4);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-05-30', 'Wonder Woman', 'Wonder Woman', 297762, 'tt0451279', 'en', 'An Amazon princess comes to the world of Man to become the greatest of the female superheroes.', 294.337037, 141, 7.2);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-03-16', 'Beauty and the Beast', 'Beauty and the Beast', 321612, 'tt2771200', 'en', 'A live-action adaptation of Disney''s version of the classic ''Beauty and the Beast'' tale of a cursed prince and a beautiful young woman who helps him break the spell.', 287.253654, 129, 6.8);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-06-28', 'Baby Driver', 'Baby Driver', 339403, 'tt3890160', 'en', 'After being coerced into working for a crime boss, a young getaway driver finds himself taking part in a heist doomed to fail.', 228.032744, 113, 7.2);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2014-10-24', 'Big Hero 6', 'Big Hero 6', 177572, 'tt2245084', 'en', 'The special bond that develops between plus-sized inflatable robot Baymax, and prodigy Hiro Hamada, who team up with a group of friends to form a band of high-tech heroes.', 213.849907, 102, 7.8);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2016-02-09', 'Deadpool', 'Deadpool', 293660, 'tt1431045', 'en', 'Deadpool tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.', 187.860492, 108, 7.4);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-04-19', 'Guardians of the Galaxy Vol. 2', 'Guardians of the Galaxy Vol. 2', 283995, 'tt3896198', 'en', 'The Guardians must fight to keep their newfound family together as they unravel the mysteries of Peter Quill''s true parentage.', 185.330992, 137, 7.6);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2009-12-10', 'Avatar', 'Avatar', 19995, 'tt0499549', 'en', 'In the 22nd century, a paraplegic Marine is dispatched to the moon Pandora on a unique mission, but becomes torn between following orders and protecting an alien civilization.', 185.070892, 162, 7.2);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2014-10-22', 'John Wick', 'John Wick', 245891, 'tt2911666', 'en', 'Ex-lunatic John Wick comes off his meds to track down the bounders that killed his dog and made off with his self-respect', 183.870374, 101, 7);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2014-10-01', 'Gone Girl', 'Gone Girl', 210577, 'tt2267998', 'en', 'With his wife''s disappearance having become the focus of an intense media circus, a man sees the spotlight turned on him when it''s suspected that he may not be innocent.', 154.801009, 145, 7.9);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2014-11-18', 'The Hunger Games: Mockingjay - Part 1', 'The Hunger Games: Mockingjay - Part 1', 131631, 'tt1951265', 'en', 'Katniss Everdeen reluctantly becomes the symbol of a mass rebellion against the autocratic Capitol.', 147.098006, 123, 6.6);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-07-11', 'War for the Planet of the Apes', 'War for the Planet of the Apes', 281338, 'tt3450958', 'en', 'Caesar and his apes are forced into a deadly conflict with an army of humans led by a ruthless Colonel. After the apes suffer unimaginable losses, Caesar wrestles with his darker instincts and begins his own mythic quest to avenge his kind. As the journey finally brings them face to face, Caesar and the Colonel are pitted against each other in an epic battle that will determine the fate of both their species and the future of the planet.', 146.161786, 140, 6.7);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2016-04-27', 'Captain America: Civil War', 'Captain America: Civil War', 271110, 'tt3498820', 'en', 'Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.', 145.882135, 147, 7.1);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '1994-09-10', 'Pulp Fiction', 'Pulp Fiction', 680, 'tt0110912', 'en', 'A burger-loving hit man, his philosophical partner, a drug-addled gangster''s moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.', 140.950236, 154, 8.3);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-05-23', 'Pirates of the Caribbean: Dead Men Tell No Tales', 'Pirates of the Caribbean: Dead Men Tell No Tales', 166426, 'tt1790809', 'en', 'Thrust into an all-new paycheck, a down-on-his-luck Capt. Jack Sparrow feels the winds of ill-fortune blowing even more strongly when deadly ghost sailors led by his old nemesis, the evil Capt. Salazar, escape from the Devil''s Triangle. Jack''s only hope of a payout lies in seeking out the legendary Trident of Numbers, but to find it, he must forge an uneasy alliance with a reasonably intelligent and pretty astronomer and a irritating young man in the British navy.', 133.82782, 129, 6.6);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2008-07-16', 'The Dark Knight', 'The Dark Knight', 155, 'tt0468569', 'en', 'Batman raises the stakes in his war on crime. With the help of Lt. Jim Gordon and District Attorney Harvey Dent, Batman sets out to dismantle the remaining criminal organizations that plague the streets. The partnership proves to be effective, but they soon find themselves prey to a reign of chaos unleashed by a rising criminal mastermind known to the terrified citizens of Gotham as the Joker.', 123.167259, 152, 8.3);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '1982-06-25', 'Blade Runner', 'Blade Runner', 78, 'tt0083658', 'en', 'In the smog-choked dystopian Los Angeles of 2019, blade runner Rick Deckard is called out of retirement to terminate a quartet of replicants who have escaped to Earth seeking their creator for a way to extend their short life spans.', 96.272374, 117, 7.9);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2012-04-25', 'The Avengers', 'The Avengers', 24428, 'tt0848228', 'en', 'When an unexpected enemy emerges and threatens global safety and security, Nick Fury, director of the international peacekeeping agency known as S.H.I.E.L.D., finds himself in need of a team to pull the world back from the brink of disaster. Spanning the globe, a daring recruitment effort begins!', 89.887648, 143, 7.4);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-06-01', 'Captain Underpants: The First Epic Movie', 'Captain Underpants: The First Epic Movie', 268531, 'tt2091256', 'en', 'Two mischievous kids hypnotize their mean elementary school principal and turn him into their comic book creation, the kind-hearted and elastic-banded Captain Underpants.', 88.561239, 89, 6.5);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-04-27', 'The Circle', 'The Circle', 339988, 'tt4287320', 'en', 'A young tech worker takes a job at a greedy Internet corporation, quickly rises up the company''s ranks, and soon finds herself in a perilous situation concerning privacy, surveillance and freedom. She comes to learn that her decisions and actions will have no effect whatsoever.', 88.439243, 110, 5.4);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-06-23', 'The Bad Batch', 'The Bad Batch', 316154, 'tt4334266', 'en', 'In a desert wasteland in Texas, a muscled cannibal breaks one important rule: don’t play with your food.', 78.8072, 119, 5.3);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2014-09-10', 'The Maze Runner', 'The Maze Runner', 198663, 'tt1790864', 'en', 'Set in a post-apocalyptic world, young Thomas is deposited in a community of boys after his memory is erased, soon learning they''re all trapped in a maze that will require him to join forces with fellow "runners" for a shot at escape.', 76.93789, 113, 7);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2015-04-01', 'Furious 7', 'Furious 7', 168259, 'tt2820852', 'en', 'Deckard Shaw seeks revenge against Dominic Toretto and his family for his comatose brother.', 27.275687, 137, 7.3);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2014-06-26', 'Dawn of the Planet of the Apes', 'Dawn of the Planet of the Apes', 119450, 'tt2103281', 'en', 'A group of scientists in San Francisco struggle to stay alive in the aftermath of a plague that is wiping out humanity, while Caesar tries to maintain dominance over his community of intelligent apes.', 75.385211, 130, 7.3);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-05-09', 'Alien: Covenant', 'Alien: Covenant', 126889, 'tt2316204', 'en', 'Bound for a remote planet on the far side of the galaxy, the crew of the colony ship ''Covenant'' discovers what is thought to be an uncharted paradise, but is actually a dark, dangerous world – which has its sole inhabitant the ''synthetic'', David, survivor of the doomed Prometheus expedition.', 72.884078, 122, 5.7);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-03-29', 'Ghost in the Shell', 'Ghost in the Shell', 315837, 'tt1219827', 'en', 'In the near future, Major is the first of her kind: a human saved from a terrible crash, then cyber-enhanced to be a perfect soldier devoted to stopping the world''s most dangerous criminals.', 68.726676, 107, 5.9);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2016-08-01', 'Boyka: Undisputed IV', 'Boyka: Undisputed IV', 348893, 'tt3344680', 'en', 'In the fourth installment of the fighting franchise, Boyka is shooting for the big leagues when an accidental death in the ring makes him question everything he stands for. When he finds out the wife of the man he accidentally killed is in trouble, Boyka offers to fight in a series of impossible battles to free her from a life of servitude', 67.955052, 87, 5.8);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2014-10-10', 'Whiplash', 'Whiplash', 244786, 'tt2582802', 'en', 'Under the direction of a ruthless instructor, a talented young drummer begins to pursue perfection at any cost, even his humanity.', 64.29999, 105, 8.3);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '1999-10-15', 'Fight Club', 'Fight Club', 550, 'tt0137523', 'en', 'A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground "fight clubs" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.', 63.869599, 139, 8.3);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-08-18', 'What Happened to Monday', 'What Happened to Monday', 406990, 'tt1536537', 'en', 'In a world where families are limited to one child due to overpopulation, a set of identical septuplets must avoid being put to a long sleep by the government and dangerous infighting while investigating the disappearance of one of their own.', 60.581223, 123, 7.3);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-07-07', 'Wish Upon', 'Wish Upon', 440597, 'tt5322012', 'en', 'A teenage girl discovers a box with magical powers, but those powers comes with a deadly price.', 59.578823, 90, 5.3);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-10-25', 'Thor: Ragnarok', 'Thor: Ragnarok', 284053, 'tt3501632', 'en', 'Thor is imprisoned on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the destruction of his homeworld and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela.', 57.283628, 0, 0);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-02-28', 'Logan', 'Logan', 263115, 'tt3315342', 'en', 'In the near future, a weary Logan cares for an ailing Professor X in a hideout on the Mexican border. But Logan''s attempts to hide from the world and his legacy are upended when a young mutant arrives, pursued by dark forces.', 54.581997, 137, 7.6);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2014-07-30', 'Guardians of the Galaxy', 'Guardians of the Galaxy', 118340, 'tt2015381', 'en', 'Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser.', 53.291601, 121, 7.9);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-06-15', '47 Meters Down', '47 Meters Down', 403119, 'tt2932536', 'en', 'Two sisters on Mexican vacation are trapped in a shark observation cage at the bottom of the ocean, with oxygen running low and great whites circling nearby, they have less than an hour of air left to figure out how to get to the surface.', 52.854103, 89, 5.1);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '1994-09-23', 'The Shawshank Redemption', 'The Shawshank Redemption', 278, 'tt0111161', 'en', 'Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.', 51.645403, 142, 8.5);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2016-02-12', 'The Last King', 'Birkebeinerne', 360249, 'tt4738360', 'no', 'Year 1206. Norway is ravaged by civil war. The King''s illegitimate son is guarded in deep secret. A boy that half of the kingdom wants to kill and two men will protect to the death. A boy named Earl Håkonsson. Birkebeiners is the story of the escape that changed Norway''s history forever.', 50.949523, 99, 5.2);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-08-03', 'The Dark Tower', 'The Dark Tower', 353491, 'tt1648190', 'en', 'The last Gunslinger, Roland Deschain, has been locked in an eternal battle with Walter O’Dim, also known as the Man in Black, determined to prevent him from toppling the Dark Tower, which holds the universe together. With the fate of the worlds at stake, good and evil will collide in the ultimate battle as only Roland can defend the Tower from the Man in Black.', 50.903593, 95, 5.7);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-02-08', 'John Wick: Chapter 2', 'John Wick: Chapter 2', 324552, 'tt4425200', 'en', 'John Wick is forced out of retirement by a former associate looking to seize control of a shadowy international assassins’ guild. Bound by a blood oath to aid him, Wick travels to Rome and does battle against some of the world’s most dangerous killers.', 49.247505, 122, 6.7);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-04-12', 'The Fate of the Furious', 'The Fate of the Furious', 337339, 'tt4630562', 'en', 'When a mysterious woman seduces Dom into the world of crime and a betrayal of those closest to him, the crew face trials that will test them as never before.', 48.573287, 136, 6.8);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '1994-07-06', 'Forrest Gump', 'Forrest Gump', 13, 'tt0109830', 'en', 'A man with a low IQ has accomplished great things in his life and been present during significant historic events - in each case, far exceeding what anyone imagined he could do. Yet, despite all the things he has attained, his one true love eludes him. ''Forrest Gump'' is the story of a man who rose above his challenges, and who proved that determination, courage, and love are more important than ability.', 48.307194, 142, 8.2);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2003-07-09', 'Pirates of the Caribbean: The Curse of the Black Pearl', 'Pirates of the Caribbean: The Curse of the Black Pearl', 22, 'tt0325980', 'en', 'Jack Sparrow, a freewheeling 17th-century pirate who roams the Caribbean Sea, butts heads with a rival pirate bent on pillaging the village of Port Royal. When the governor''s daughter is kidnapped, Sparrow decides to help the girl''s love save her. But their seafaring mission is hardly simple.', 47.326665, 143, 7.5);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-03-04', 'Security', 'Security', 460846, 'tt3501112', 'en', 'An ex-special services veteran, down on his luck and desperate for work, takes a job as a security guard at a run-down mall in a rough area of town. On his first night on the job, he opens the door to a distraught and desperate young girl who has fled the hijacking of a Police motorcade that was transporting her to testify as a witness in a trial. Hot on her heels is the psychopathic hijacker and his team of henchmen, who will stop at nothing to extract and eliminate the witness.', 47.114366, 87, 6);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2016-06-29', 'The Legend of Tarzan', 'The Legend of Tarzan', 258489, 'tt0918940', 'en', 'Tarzan, having acclimated to life in London, is called back to his former home in the jungle to investigate the activities at a mining encampment.', 45.38298, 109, 5.5);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2017-04-27', 'King Arthur: Legend of the Sword', 'King Arthur: Legend of the Sword', 274857, 'tt1972591', 'en', 'When the child Arthur’s father is murdered, Vortigern, Arthur’s uncle, seizes the crown. Robbed of his birthright and with no idea who he truly is, Arthur comes up the hard way in the back alleys of the city. But once he pulls the sword Excalibur from the stone, his life is turned upside down and he is forced to acknowledge his true legacy... whether he likes it or not.', 44.251369, 126, 6.5);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2016-10-25', 'Doctor Strange', 'Doctor Strange', 284052, 'tt1211837', 'en', 'After his career is destroyed, a brilliant but arrogant surgeon gets a new lease on life when a sorcerer takes him under his wing and trains him to defend the world against evil.', 43.847654, 115, 7.1);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2016-08-02', 'Suicide Squad', 'Suicide Squad', 297761, 'tt1386697', 'en', 'From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.', 42.965027, 123, 5.9);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '1977-05-25', 'Star Wars', 'Star Wars', 11, 'tt0076759', 'en', 'Princess Leia is captured and held hostage by the evil Imperial forces in their effort to take over the galactic Empire. Venturesome Luke Skywalker and dashing captain Han Solo team together with the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and restore peace and justice in the Empire.', 42.149697, 121, 8.1);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '2015-06-25', 'Ted 2', 'Ted 2', 214756, 'tt2637276', 'en', 'Newlywed couple Ted and Tami-Lynn want to have a baby, but in order to qualify to be a parent, Ted will have to prove he''s a person in a court of law.', 42.061481, 115, 6.2);
INSERT INTO public.movies (movie_id, creation_date, release_date, title, original_title, tmdb_id, imdb_id, original_language, overview, popularity, runtime, vote_average) VALUES (DEFAULT, '2020-09-23 15:24:35.483125', '1993-11-29', 'Schindler''s List', 'Schindler''s List', 424, 'tt0108052', 'en', 'The true story of how businessman Oskar Schindler saved over a thousand Jewish lives from the Nazis while they worked as slaves in his factory during World War II.', 41.725123, 195, 8.3);

INSERT INTO public.users (user_id, creation_date, username, password, name, email, description, enabled) VALUES (DEFAULT, '2020-09-20 12:56:13.160000', 'testUser', '$2a$10$O6SNpY56M8b33xKMe92tEOkEezVsln0ocrREUkCK.OC1JY7G1Nfsm', 'Intelli', 'prueba@prueba.com', '', true);
INSERT INTO public.users (user_id, creation_date, username, password, name, email, description, enabled) VALUES (DEFAULT, '2020-09-20 12:57:12.108000', 'testUser2', '$2a$10$95/PHiCzqkRvshToPzjym.0oBs1kmpRIyto8dD6oQ0NN31utMtD6i', 'Gitkraken', 'test@abc.com', '', false);
INSERT INTO public.users (user_id, creation_date, username, password, name, email, description, enabled) VALUES (DEFAULT, '2020-09-20 12:58:17.441000', 'testUser3', '$2a$10$m0KrIioERjnPfLxo6vlaJuwnwDyXDSyGnW9AoLapXV9jkZ/nLVxxG', 'Jorgito', 'Jorge@moovify.com', '', true);
INSERT INTO public.users (user_id, creation_date, username, password, name, email, description, enabled) VALUES (DEFAULT, '2020-09-23 12:28:04.671000', 'testUser4', '$2a$10$Lve0Aj3zbXft74IulXFogevrFkA9C507ZaOsF01V1XVJf8Ypc9ZpO', 'Juancito', 'fanNumber1@gmail.com', '', true);

INSERT INTO public.user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (3, 2);
INSERT INTO public.user_role (user_id, role_id) VALUES (4, 3);

INSERT INTO public.posts (post_id, creation_date, title, user_id, category_id, word_count, body, enabled) VALUES (DEFAULT, '2020-09-09 15:26:31.440000', 'Post Test 1', 1, 1, 554, 'body_test', true);
INSERT INTO public.posts (post_id, creation_date, title, user_id, category_id, word_count, body, enabled) VALUES (DEFAULT, '2020-09-09 15:32:57.415000', 'Post Test 2', 2, 1, 2886, 'body_test', true);
INSERT INTO public.posts (post_id, creation_date, title, user_id, category_id, word_count, body, enabled) VALUES (DEFAULT, '2020-09-09 15:49:38.898000', 'Post Test 3', 1, 1, 932, 'body_test', true);
INSERT INTO public.posts (post_id, creation_date, title, user_id, category_id, word_count, body, enabled) VALUES (DEFAULT, '2020-09-09 15:59:38.898000', 'Post Test 4', 2, 1, 932, 'body_test', false);

INSERT INTO public.post_movie (post_id, movie_id) VALUES (1, 8);
INSERT INTO public.post_movie (post_id, movie_id) VALUES (1, 9);
INSERT INTO public.post_movie (post_id, movie_id) VALUES (3, 10);

INSERT INTO public.tags (post_id, tag) VALUES (3, 'Lorem Ipsum');
INSERT INTO public.tags (post_id, tag) VALUES (1, 'Lorem Ipsum');
INSERT INTO public.tags (post_id, tag) VALUES (1, 'Review');
INSERT INTO public.tags (post_id, tag) VALUES (2, 'Documental');
INSERT INTO public.tags (post_id, tag) VALUES (2, 'Premiere');
INSERT INTO public.tags (post_id, tag) VALUES (3, 'Review');

INSERT INTO public.comments (comment_id, parent_id, post_id, user_id, creation_date, body, enabled) VALUES (DEFAULT, null, 1, 1, '2020-09-09 15:54:42.389000', 'Comment 1', true);
INSERT INTO public.comments (comment_id, parent_id, post_id, user_id, creation_date, body, enabled) VALUES (DEFAULT, null, 1, 2, '2020-09-09 15:55:09.674000', 'Comment 2', true);
INSERT INTO public.comments (comment_id, parent_id, post_id, user_id, creation_date, body, enabled) VALUES (DEFAULT, 1, 1, 1, '2020-09-09 15:56:00.109000', 'Comment 3', true);
INSERT INTO public.comments (comment_id, parent_id, post_id, user_id, creation_date, body, enabled) VALUES (DEFAULT, 1, 1, 3, '2020-09-09 15:56:28.282000', 'Comment 4', true);
INSERT INTO public.comments (comment_id, parent_id, post_id, user_id, creation_date, body, enabled) VALUES (DEFAULT, 3, 1, 3, '2020-09-09 15:57:03.564000', 'Comment 5', true);
INSERT INTO public.comments (comment_id, parent_id, post_id, user_id, creation_date, body, enabled) VALUES (DEFAULT, 5, 1, 2, '2020-09-09 15:57:28.040000', 'Comment 6', true);
INSERT INTO public.comments (comment_id, parent_id, post_id, user_id, creation_date, body, enabled) VALUES (DEFAULT, null, 3, 1, '2020-09-09 16:00:44.957000', 'Comment 7', true);
INSERT INTO public.comments (comment_id, parent_id, post_id, user_id, creation_date, body, enabled) VALUES (DEFAULT, null, 3, 2, '2020-09-09 16:01:12.985000', 'Comment 8', true);
INSERT INTO public.comments (comment_id, parent_id, post_id, user_id, creation_date, body, enabled) VALUES (DEFAULT, 8, 3, 2, '2020-09-09 16:01:57.509000', 'Comment 9', true);
INSERT INTO public.comments (comment_id, parent_id, post_id, user_id, creation_date, body, enabled) VALUES (DEFAULT, 8, 3, 1, '2020-09-09 16:02:25.878000', 'Comment 10', true);

INSERT INTO public.user_verification_token (token_id, user_id, token, expiry) VALUES (1, 4, '72b03561-eb02-40ec-ad19-9278584bdd4d', '2020-09-24 12:28:05.212000');

INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (10751, 211672);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (16, 211672);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (12, 211672);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (35, 211672);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (28, 297762);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (12, 297762);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (14, 297762);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (10751, 321612);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (14, 321612);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (10749, 321612);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (28, 339403);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (80, 339403);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (12, 177572);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (10751, 177572);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (16, 177572);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (28, 177572);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (35, 177572);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (28, 293660);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (12, 293660);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (35, 293660);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (28, 283995);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (12, 283995);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (35, 283995);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (878, 283995);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (28, 19995);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (12, 19995);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (14, 19995);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (878, 19995);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (28, 245891);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (53, 245891);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (9648, 210577);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (53, 210577);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (18, 210577);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (878, 131631);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (12, 131631);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (53, 131631);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (18, 281338);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (878, 281338);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (10752, 281338);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (12, 271110);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (28, 271110);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (878, 271110);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (53, 680);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (80, 680);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (12, 166426);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (28, 166426);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (14, 166426);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (35, 166426);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (18, 155);
INSERT INTO public.movie_to_movie_category (tmdb_category_id, tmdb_id) VALUES (28, 155);

INSERT INTO public.posts_likes(post_id, user_id, value) VALUES (1,1,1);
INSERT INTO public.posts_likes(post_id, user_id, value) VALUES (1,2,1);
INSERT INTO public.posts_likes(post_id, user_id, value) VALUES (1,3,1);
INSERT INTO public.posts_likes(post_id, user_id, value) VALUES (2,2,1);
INSERT INTO public.posts_likes(post_id, user_id, value) VALUES (3,2,-1);