INSERT INTO accounts (uuid, nickname, email, is_public, salt, phone, role, is_expired, is_locked, created_at, home_page_url, description, followers_number, following_number, number_of_trips, profile_picture_id) VALUES
                                                                                                                                                                                                                       ('550e8400-e29b-41d4-a716-446655440005', 'user1', 'user1@example.com', true, 'salt1', '1234567890', 'USER', false, false, '2023-01-01', 'http://example.com/user1', 'Description for user 1', 100, 150, 10, NULL),
                                                                                                                                                                                                                       ('550e8400-e29b-41d4-a716-446655440006', 'user2', 'user2@example.com', false, 'salt2', '1234567891', 'USER', false, true, '2023-02-01', 'http://example.com/user2', 'Description for user 2', 200, 250, 20, NULL),
                                                                                                                                                                                                                       ('550e8400-e29b-41d4-a716-446655440007', 'user3', 'user3@example.com', true, 'salt3', '1234567892', 'ADMIN', true, false, '2023-03-01', 'http://example.com/user3', 'Description for user 3', 300, 350, 30, NULL),
                                                                                                                                                                                                                       ('550e8400-e29b-41d4-a716-446655440008', 'user4', 'user4@example.com', true, 'salt4', '1234567893', 'USER', false, false, '2023-04-01', 'http://example.com/user4', 'Description for user 4', 400, 450, 40, NULL),
                                                                                                                                                                                                                       ('550e8400-e29b-41d4-a716-446655440009', 'user5', 'user5@example.com', false, 'salt5', '1234567894', 'USER', false, true, '2023-05-01', 'http://example.com/user5', 'Description for user 5', 500, 550, 50, NULL);

INSERT INTO posts (uuid, content, date_of_post, is_expired, is_locked, commments_number, reactions_number, publisher_id) VALUES
                                                                                                                             ('550e8400-e29b-41d4-a716-446655440000', 'Example post content 1', '2023-01-01', false, false, 5, 10, 1),
                                                                                                                             ('550e8400-e29b-41d4-a716-446655440001', 'Example post content 2', '2023-02-01', true, true, 15, 20, 1),
                                                                                                                             ('550e8400-e29b-41d4-a716-446655440002', 'Example post content 3', '2023-03-01', false, false, 25, 30, 3),
                                                                                                                             ('550e8400-e29b-41d4-a716-446655440003', 'Example post content 4', '2023-04-01', true, false, 35, 40, 4),
                                                                                                                             ('550e8400-e29b-41d4-a716-446655440004', 'Example post content 5', '2023-05-01', false, true, 45, 50, 5);


INSERT INTO groups (
    uuid,
    name,
    number_of_members,
    is_public,
    description,
    rules,
    date_of_creation,
    home_page_url,
    location_longitude,
    location_latitude,
    location_scope_id,
    owner_id,
    profile_picture_id
)
VALUES
    (
        'a9b9e209-b58e-4f7d-a24e-63bbf3d7c0b5',
        'Sample Group 1',
        25,
        TRUE,
        'This is a description for group 1.',
        'Rules for group 1',
        '2024-11-06',
        'https://samplegroup1.com',
        19.1456789,
        51.7521245,
        null,
        1,
        null
    ),
    (
        'f6c5b58b-d64f-4729-bdb7-28935ebf964a',
        'Sample Group 2',
        10,
        FALSE,
        'This is a description for group 2.',
        'Rules for group 2',
        '2024-10-01',
        'https://samplegroup2.com',
        19.2623456,
        51.8354321,
        null,
        3,
        null
    );

INSERT INTO events_statuses (name) VALUES
                                       ('created'), ('in progress'), ('finished');



INSERT INTO events (
    uuid,
    description,
    rules,
    is_public,
    date_of_creation,
    number_of_participants,
    actual_number_of_participants,
    max_number_of_participants,
    start_longitude,
    start_latitude,
    stop_longitude,
    stop_latitude,
    destination_longitude,
    destination_latitude,
    home_page_url,
    event_status_id,
    relation_id,
    owner_id,
    icon_id
)
VALUES
    (
        'b8f6c199-8b69-4b4b-b06d-3d8d12c07c8c',
        'This is a sample event for testing purposes.',
        'Follow the event rules carefully.',
        TRUE,
        '2024-11-06',
        100,
        50,
        200,
        19.1456789,
        51.7521245,
        19.2623456,
        51.8354321,
        19.3445678,
        51.9001234,
        'https://sampleevent1.com',
        3,
        null,
        1,
        null
    ),
    (
        'f9c6a345-0f0a-45f4-9302-63f122e9e8ea',
        'Another example event description for testing.',
        'Different rules for this event.',
        FALSE,
        '2024-10-01',
        50,
        25,
        100,
        19.4567890,
        51.9123456,
        19.5678901,
        51.9234567,
        19.6789012,
        51.9345678,
        'https://sampleevent2.com',
        2,
        null,
        4,
        null
    );


INSERT INTO personal_posts (post_id) VALUES (1), (5);
INSERT INTO groups_posts (post_id, group_id) VALUES (1,1), (3,2);
INSERT INTO events_posts (post_id, event_id) VALUES (2,1), (4,2);


