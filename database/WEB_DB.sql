--
-- PostgreSQL database dump
--

-- Dumped from database version 11.0
-- Dumped by pg_dump version 12.0

-- Started on 2020-05-21 18:03:33

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 2899 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


--
-- TOC entry 203 (class 1259 OID 16670)
-- Name: file_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.file_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.file_id_seq OWNER TO postgres;

SET default_tablespace = '';

--
-- TOC entry 204 (class 1259 OID 16672)
-- Name: file; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.file (
    id integer DEFAULT nextval('public.file_id_seq'::regclass) NOT NULL,
    name character varying(200) NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.file OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 33178)
-- Name: password_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.password_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.password_id_seq OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 41361)
-- Name: password; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.password (
    id integer DEFAULT nextval('public.password_id_seq'::regclass) NOT NULL,
    value character varying(30) NOT NULL,
    created_date_time timestamp with time zone NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.password OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16427)
-- Name: publication; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.publication (
    id integer NOT NULL,
    date_time timestamp with time zone NOT NULL,
    title character varying(50) NOT NULL,
    content character varying(500) NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.publication OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 49573)
-- Name: publication_file_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.publication_file_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 32000
    CACHE 1;


ALTER TABLE public.publication_file_id_seq OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 49553)
-- Name: publication_file; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.publication_file (
    id integer DEFAULT nextval('public.publication_file_id_seq'::regclass) NOT NULL,
    publication_id integer NOT NULL,
    file_id integer NOT NULL
);


ALTER TABLE public.publication_file OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 16425)
-- Name: publication_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.publication_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.publication_id_seq OWNER TO postgres;

--
-- TOC entry 2900 (class 0 OID 0)
-- Dependencies: 199
-- Name: publication_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.publication_id_seq OWNED BY public.publication.id;


--
-- TOC entry 201 (class 1259 OID 16571)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    id integer NOT NULL,
    role character varying(10) NOT NULL,
    priority integer NOT NULL
);


ALTER TABLE public.role OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16580)
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.role_id_seq OWNER TO postgres;

--
-- TOC entry 2901 (class 0 OID 0)
-- Dependencies: 202
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id;


--
-- TOC entry 198 (class 1259 OID 16395)
-- Name: user_; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_ (
    id integer NOT NULL,
    name character varying(20) NOT NULL,
    surname character varying(30) NOT NULL,
    about character varying(100),
    username character varying(20) NOT NULL,
    avatar character varying(255)
);


ALTER TABLE public.user_ OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16679)
-- Name: user_file_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_file_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_file_id_seq OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 16393)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_id_seq OWNER TO postgres;

--
-- TOC entry 2902 (class 0 OID 0)
-- Dependencies: 197
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_id_seq OWNED BY public.user_.id;


--
-- TOC entry 207 (class 1259 OID 33191)
-- Name: user_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_role_id_seq OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 33193)
-- Name: user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_role (
    id integer DEFAULT nextval('public.user_role_id_seq'::regclass) NOT NULL,
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.user_role OWNER TO postgres;

--
-- TOC entry 2727 (class 2604 OID 16522)
-- Name: publication id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publication ALTER COLUMN id SET DEFAULT nextval('public.publication_id_seq'::regclass);


--
-- TOC entry 2728 (class 2604 OID 16582)
-- Name: role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);


--
-- TOC entry 2726 (class 2604 OID 16521)
-- Name: user_ id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_ ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);


--
-- TOC entry 2886 (class 0 OID 16672)
-- Dependencies: 204
-- Data for Name: file; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.file (id, name, user_id) FROM stdin;
34	5e0fe33c-8493-5d90-9670-febb8e307226.jpg	3
35	cfdad248-e6b9-5f65-801d-d123a646d6c2.jpg	3
36	99790c93-cc8e-5789-8eed-d913ffb3a6a1.jpg	3
37	24420220-df24-5306-9285-9f039978d461.jpg	3
38	7898783b-741d-55e9-b989-4017db7d96ca.jpg	3
39	47869eda-f63a-536e-9c73-906bd2b7a189.jpg	3
40	8feed58e-5617-5fb5-a616-a2d29c1044f2.jpg	3
41	eae917c4-cc40-50d2-bf42-765187287ce4.jpg	3
42	09c43777-43d6-5590-b417-4e976cec9343.jpg	5
43	36519c38-e9eb-55b0-ad15-e46e48c951b2.jpg	3
44	209f8553-1a7d-5df3-a7b8-c130f2424a76.jpg	3
45	d50be0e2-9877-56d1-8423-1743f4c77431.jpg	195
46	2043fa4f-199f-595a-954e-4f7602701d85.jpg	195
47	6b1b24a7-4a29-5a64-84a2-c70f56b373f4.jpg	195
48	36a288d6-89f1-5ab7-b535-5dec1f60ee35.jpg	195
49	9f6b1ce0-0528-5e03-89bd-f7cd35eee26b.png	195
50	9825719c-c53c-54de-bc98-1eec417273d3.jpg	195
51	7570483f-31d4-5b69-9710-b8b75707284b.jpg	195
52	bbefd475-82dc-5e8f-9b21-894b858f9ae3.jpg	195
53	fcbba5fc-15f6-542c-8e7e-ca5abfffc9c4.jpg	195
54	7ef1cf62-9f88-5fc2-b02b-2ff54b17e596.jpg	195
55	5fe31421-d51e-581e-8685-8be132e0695c.jpg	195
56	ef73eba4-4a57-5a41-96ed-cdf9712510fe.jpg	195
57	d1c82d4d-219c-5618-97c5-96a560999c69.jpg	195
58	6f6120e8-bb05-558a-af24-b44308ed1d26.jpg	195
59	970f9c64-9386-5fb5-9d5c-f6d13f96fc41.jpg	195
60	c1a23f8e-3fd4-55b3-a6b7-0e134799af44.jpg	195
61	cb598bf6-012e-510f-b337-b48f9dd3bb5b.jpg	195
62	fc98fd64-92ad-5aa1-9d8c-ab0bd09f5a3a.jpg	195
\.


--
-- TOC entry 2891 (class 0 OID 41361)
-- Dependencies: 209
-- Data for Name: password; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.password (id, value, created_date_time, user_id) FROM stdin;
105	madmax	2020-01-29 06:12:37.096229+03	5
106	jacksparrow	2020-01-29 06:12:37.096229+03	6
107	tester	2020-01-29 06:12:37.096229+03	21
108	alexey	2020-01-29 06:12:37.096229+03	3
109	alice	2020-01-29 06:12:37.096229+03	16
118	refactor	2020-01-30 04:15:49.313425+03	191
119	refactor2	2020-02-01 02:41:10.559323+03	192
120	refactor3	2020-02-01 19:39:03.620953+03	193
121	refacor5	2020-02-08 15:26:14.441085+03	194
122	852258654	2020-02-22 18:06:25.198523+03	195
\.


--
-- TOC entry 2882 (class 0 OID 16427)
-- Dependencies: 200
-- Data for Name: publication; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.publication (id, date_time, title, content, user_id) FROM stdin;
10	2018-11-01 03:27:53.003393+03	I'm Alice	Hi, everybody!	16
11	2018-11-01 03:28:32.948557+03	In eu bibendum purus metus.	Ut metus lorem, porta nec feugiat at, luctus vel nisl. Suspendisse vestibulum justo vel velit maximus, sit amet posuere libero tincidunt. Duis congue consectetur sollicitudin. Nam tristique tortor vel lobortis.	16
12	2018-11-05 03:34:42.531543+03	I'm soo MAD!	Got bait? Watch my films!	5
3	2018-10-27 17:40:32.270386+03	Maecenas a imperdiet ex metus.	Quisque enim lectus, condimentum imperdiet efficitur sit amet, iaculis eu massa. Donec aliquam elit sit amet tellus malesuada, vitae fringilla nisl feugiat. Duis facilisis justo elit, nec tincidunt leo fermentum in. In tortor eros, fringilla consequat nulla blandit, condimentum sollicitudin posuere.	3
8	2018-10-30 04:16:13.966616+03	Cras malesuada tortor non metus orci aliquam.	Integer pulvinar at enim vulputate condimentum. Etiam eu arcu sem. In hac habitasse platea dictumst. Nullam at ex tortor. Vivamus et elementum nunc, sed bibendum neque. Integer pretium diam orci, sit amet congue sem dapibus lacinia. Donec sit amet neque mollis, auctor augue eget, dapibus urna. Donec non ultrices dolor. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla facilisi. Nunc nibh quam turpis duis.	3
25	2020-01-28 02:12:47.53627+03	fsdfsdf	sdfsdfsdf	16
24	2020-01-30 04:08:03.446044+03	aaaa+++ds	aaaaaaaaaaaaaaaaaa	3
35	2020-02-01 21:03:51.764225+03	hjlksfds	gjkyhgfvcv	193
30	2020-02-01 02:45:50.968679+03	gdfgd	gdfgdfgf	192
36	2020-02-01 21:49:47.040773+03	dgffg	gfgf	193
38	2020-02-07 02:00:10.0393+03	aaaaa	aaa+++++	5
39	2020-02-07 02:02:37.053716+03	ttrujty	ytuityutyu	5
\.


--
-- TOC entry 2892 (class 0 OID 49553)
-- Dependencies: 210
-- Data for Name: publication_file; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.publication_file (id, publication_id, file_id) FROM stdin;
1	38	34
\.


--
-- TOC entry 2883 (class 0 OID 16571)
-- Dependencies: 201
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.role (id, role, priority) FROM stdin;
10	admin	1
11	user	2
12	guest	3
\.


--
-- TOC entry 2880 (class 0 OID 16395)
-- Dependencies: 198
-- Data for Name: user_; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_ (id, name, surname, about, username, avatar) FROM stdin;
21	test	user	test user	tester	\N
191	refactor	refactor		refactor	\N
6	Jack	Sparrow	Cpt. Jack	jacksparrow	\N
192	refactor2	refactor2	gdfghfdsd	refactor2	\N
193	refactor3	refactor3	hgh	refactor3	\N
5	Mad	Max	Really mad guy!!!	madmax	09c43777-43d6-5590-b417-4e976cec9343.jpg
16	alice	wonderland	alice from wonderland	alice	\N
194	refacor5	refacor5	refacor5	refacor5	\N
3	John	Cooper	awesome guy	alexey	209f8553-1a7d-5df3-a7b8-c130f2424a76.jpg
195	alexey	syromyatnikov	it is me	excalibur	fc98fd64-92ad-5aa1-9d8c-ab0bd09f5a3a.jpg
\.


--
-- TOC entry 2890 (class 0 OID 33193)
-- Dependencies: 208
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_role (id, user_id, role_id) FROM stdin;
4	5	11
5	5	12
6	6	11
7	6	12
8	16	11
9	16	12
10	21	11
11	21	12
113	191	12
114	191	11
115	192	12
116	192	11
117	193	12
118	193	11
119	194	12
120	194	11
32	3	10
33	3	11
34	3	12
121	195	12
122	195	11
\.


--
-- TOC entry 2903 (class 0 OID 0)
-- Dependencies: 203
-- Name: file_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.file_id_seq', 65, true);


--
-- TOC entry 2904 (class 0 OID 0)
-- Dependencies: 206
-- Name: password_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.password_id_seq', 122, true);


--
-- TOC entry 2905 (class 0 OID 0)
-- Dependencies: 211
-- Name: publication_file_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.publication_file_id_seq', 4, true);


--
-- TOC entry 2906 (class 0 OID 0)
-- Dependencies: 199
-- Name: publication_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.publication_id_seq', 43, true);


--
-- TOC entry 2907 (class 0 OID 0)
-- Dependencies: 202
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.role_id_seq', 12, true);


--
-- TOC entry 2908 (class 0 OID 0)
-- Dependencies: 205
-- Name: user_file_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_file_id_seq', 1, false);


--
-- TOC entry 2909 (class 0 OID 0)
-- Dependencies: 197
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 195, true);


--
-- TOC entry 2910 (class 0 OID 0)
-- Dependencies: 207
-- Name: user_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_role_id_seq', 122, true);


--
-- TOC entry 2742 (class 2606 OID 49552)
-- Name: file file_name_unique_con; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT file_name_unique_con UNIQUE (name);


--
-- TOC entry 2744 (class 2606 OID 33087)
-- Name: file file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT file_pkey PRIMARY KEY (id);


--
-- TOC entry 2748 (class 2606 OID 41365)
-- Name: password password_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.password
    ADD CONSTRAINT password_pkey PRIMARY KEY (id);


--
-- TOC entry 2750 (class 2606 OID 49557)
-- Name: publication_file publication_file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publication_file
    ADD CONSTRAINT publication_file_pkey PRIMARY KEY (id);


--
-- TOC entry 2740 (class 2606 OID 16602)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 2734 (class 2606 OID 16403)
-- Name: user_ user_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_
    ADD CONSTRAINT user_data_pkey PRIMARY KEY (id);


--
-- TOC entry 2738 (class 2606 OID 16435)
-- Name: publication user_publication_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publication
    ADD CONSTRAINT user_publication_pkey PRIMARY KEY (id);


--
-- TOC entry 2746 (class 2606 OID 33198)
-- Name: user_role user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (id);


--
-- TOC entry 2736 (class 2606 OID 41360)
-- Name: user_ username_unique_con; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_
    ADD CONSTRAINT username_unique_con UNIQUE (username);


--
-- TOC entry 2756 (class 2606 OID 49558)
-- Name: publication_file file_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publication_file
    ADD CONSTRAINT file_id_fk FOREIGN KEY (file_id) REFERENCES public.file(id) NOT VALID;


--
-- TOC entry 2757 (class 2606 OID 49563)
-- Name: publication_file publication_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publication_file
    ADD CONSTRAINT publication_id_fk FOREIGN KEY (publication_id) REFERENCES public.publication(id) NOT VALID;


--
-- TOC entry 2754 (class 2606 OID 33204)
-- Name: user_role role_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- TOC entry 2751 (class 2606 OID 16436)
-- Name: publication user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publication
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.user_(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2752 (class 2606 OID 33161)
-- Name: file user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.user_(id);


--
-- TOC entry 2753 (class 2606 OID 33199)
-- Name: user_role user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.user_(id);


--
-- TOC entry 2755 (class 2606 OID 41366)
-- Name: password user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.password
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.user_(id) NOT VALID;


-- Completed on 2020-05-21 18:03:33

--
-- PostgreSQL database dump complete
--

