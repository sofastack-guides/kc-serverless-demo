CREATE TABLE IF NOT EXISTS books
(
    id          INT AUTO_INCREMENT,
    title       VARCHAR(255) NOT NULL,
    author      VARCHAR(255) NOT NULL,
    image       TEXT,
    description TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS reviews
(
    id      INT AUTO_INCREMENT,
    book_id INT,
    name    VARCHAR(255) NOT NULL,
    score   INT,
    date    TEXT,
    content TEXT,
    PRIMARY KEY (id)
);

TRUNCATE books;

INSERT into books (title, author, image, description)
values ("Moby-Dick", "Herman Melville", "https://images.gr-assets.com/books/1327940656l/153747.jpg",
        "It is the horrible texture of a fabric that should be woven of ships' cables and hawsers. A Polar wind blows through it, and birds of prey hover over it.\" \n\nSo Melville wrote of his masterpiece, one of the greatest works of imagination in literary history. In part, Moby-Dick is the story of an eerily compelling madman pursuing an unholy war against a creature as vast and dangerous and unknowable as the sea itself. But more than just a novel of adventure, more than an encyclopaedia of whaling lore and legend, the book can be seen as part of its author's lifelong meditation on America. Written with wonderfully redemptive humour, Moby-Dick is also a profound inquiry into character, faith, and the nature of perception.\n\nThis edition of Moby-Dick, which reproduces the definitive text of the novel, includes invaluable explanatory notes, along with maps, illustrations, and a glossary of nautical terms.");

INSERT into books (title, author, image, description)
values ("Walden", "Henry David Thoreau", "https://images.gr-assets.com/books/1465675526l/16902.jpg",
        "Originally published in 1854, Walden; or, Life in the Woods, is a vivid account of the time that Henry D. Thoreau lived alone in a secluded cabin at Walden Pond. It is one of the most influential and compelling books in American literature. This new paperback edition-introduced by noted American writer John Updike-celebrates the 150th anniversary of this classic work. Much of Walden's material is derived from Thoreau's journals and contains such engaging pieces as \"Reading\" and \"The Pond in the Winter\" Other famous sections involve Thoreau's visits with a Canadian woodcutter and with an Irish family, a trip to Concord, and a description of his bean field. This is the complete and authoritative text of Walden-as close to Thoreau's original intention as all available evidence allows. For the student and for the general reader, this is the ideal presentation of Thoreau's great document of social criticism and dissent. ");

INSERT into books (title, author, image, description)
values ("Anna Karenina", "Leo Tolstoy", "https://images.gr-assets.com/books/1546091617l/15823480.jpg",
        "Acclaimed by many as the world's greatest novel, Anna Karenina provides a vast panorama of contemporary life in Russia and of humanity in general. In it Tolstoy uses his intense imaginative insight to create some of the most memorable characters in literature. Anna is a sophisticated woman who abandons her empty existence as the wife of Karenin and turns to Count Vronsky to fulfil her passionate nature - with tragic consequences. Levin is a reflection of Tolstoy himself, often expressing the author's own views and convictions.\n\nThroughout, Tolstoy points no moral, merely inviting us not to judge but to watch. As Rosemary Edmonds comments, 'He leaves the shifting patterns of the kaleidoscope to bring home the meaning of the brooding words following the title, 'Vengeance is mine, and I will repay.");

INSERT into books (title, author, image, description)
values ("Romeo and Juliet", "William Shakespeare", "https://images.gr-assets.com/books/1554213063l/18135.jpg",
        "In Romeo and Juliet, Shakespeare creates a violent world, in which two young people fall in love. It is not simply that their families disapprove; the Montagues and the Capulets are engaged in a blood feud.\n\nIn this death-filled setting, the movement from love at first sight to the lovers’ final union in death seems almost inevitable. And yet, this play set in an extraordinary world has become the quintessential story of young love. In part because of its exquisite language, it is easy to respond as if it were about all young lovers.");

INSERT into books (title, author, image, description)
values ("The Little Prince", "Richard Howard", "https://images.gr-assets.com/books/1367545443l/157993.jpg",
        "Moral allegory and spiritual autobiography, The Little Prince is the most translated book in the French language. With a timeless charm it tells the story of a little boy who leaves the safety of his own tiny planet to travel the universe, learning the vagaries of adult behaviour through a series of extraordinary encounters. His personal odyssey culminates in a voyage to Earth and further adventures.");


