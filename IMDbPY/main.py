__author__ = 'paulo'

from imdb import IMDb
import re
import requests
import json

dic_movies = {}
dic_tv_shows = {}
dic_people = {}
imdbpy = IMDb('http', timeout=120)

headers = {'user-agent': "Automated data scraper; won't make too many requests"}

# based on the code available at https://gist.github.com/aksiksi/3067482


def scrape_movie_ids(movie_url="http://www.imdb.com/chart/top"):
    """Scrapes the IDs of IMDB's Top 250 movies."""
    source = requests.post(movie_url, headers=headers).text
    # print(source)
    return re.findall(r"\ seen-widget-tt(\w+) ", source)


def add_person_to_dictionary(list, role, video):
    global cont_person
    for item in list:
        temp_id = 'nm' + item.getID()

        if temp_id not in dic_people:
            temp_person = imdbpy.get_person(item.getID())
            print '\t', cont_person, temp_id
            cont_person += 1

            dic_people[temp_id] = {}
            dic_people[temp_id]['hasID'] = temp_id
            dic_people[temp_id]['hasName'] = str(temp_person['name'].encode('ascii', 'ignore').decode('ascii'))
            if 'birth date' in temp_person.keys():
                dic_people[temp_id]['hasBirthDate'] = str(temp_person['birth date'])
            else:
                dic_people[temp_id]['hasBirthDate'] = ''
            if 'mini biography' in temp_person.keys():
                dic_people[temp_id]['hasBiography'] = str(' '.join(temp_person['mini biography']).encode('ascii', 'ignore').decode('ascii'))
            else:
                dic_people[temp_id]['hasBiography'] = ''
            if 'full-size headshot' in temp_person.keys():
                dic_people[temp_id]['hasPhoto'] = str(temp_person['full-size headshot'])
            else:
                dic_people[temp_id]['hasPhoto'] = ''

            dic_people[temp_id]['isWriterIn'] = []
            dic_people[temp_id]['isActorIn'] = []
            dic_people[temp_id]['isDirectorIn'] = []

        dic_people[temp_id][role].append(video)

if __name__ == '__main__':
    ids_movies = scrape_movie_ids()
    ids_tv_shows = scrape_movie_ids("http://www.imdb.com/chart/toptv")

    # ids_movies = ['0117951']

    global cont_person

    count = 1
    for id_movie in ids_movies:
        temp_movie = imdbpy.get_movie(id_movie)
        temp_id = str('tt' + id_movie)
        print count, temp_id
        count += 1

        dic_movies[temp_id] = {}
        dic_movies[temp_id]['hasTitle'] = str(temp_movie['title'].encode('ascii', 'ignore').decode('ascii'))
        dic_movies[temp_id]['hasYear'] = temp_movie['year']
        dic_movies[temp_id]['hasRuntime'] = int(re.findall(r'\d+', temp_movie['runtimes'][0])[0])
        dic_movies[temp_id]['hasRating'] = temp_movie['rating']
        dic_movies[temp_id]['hasPlot'] = str(' '.join(temp_movie['plot']).encode('ascii', 'ignore').decode('ascii'))
        dic_movies[temp_id]['hasID'] = temp_id
        dic_movies[temp_id]['hasCover'] = str(temp_movie['full-size cover url'])
        dic_movies[temp_id]['hasGenre'] = [str(x) for x in temp_movie['genres']]

        cont_person = 1
        add_person_to_dictionary(temp_movie['writer'][0:min(5, len(temp_movie['writer']))], 'isWriterIn', temp_id)
        cont_person = 1
        add_person_to_dictionary(temp_movie['director'][0:min(5, len(temp_movie['director']))], 'isDirectorIn', temp_id)
        cont_person = 1
        add_person_to_dictionary(temp_movie['cast'][0:min(15, len(temp_movie['cast']))], 'isActorIn', temp_id)

    with open('dic_movies.json', 'w') as fp:
        json.dump(dic_movies, fp)

    with open('dic_tv_shows.json', 'w') as fp:
        json.dump(dic_tv_shows, fp)

    with open('dic_people.json', 'w') as fp:
        json.dump(dic_people, fp)


    '''
    with open('dic_people.json', 'r') as fp:
        dic_people = json.load(fp)
    '''

    print "len movie: ",    len(dic_movies)
    print "len people: ",   len(dic_people)
    print "len tv show: ",  len(dic_tv_shows)

    count = 1
    for ids_tv_show in ids_tv_shows:
        temp_movie = imdbpy.get_movie(ids_tv_show)

        temp_id = str('ts' + ids_tv_show)
        print count, temp_id
        count += 1

        dic_tv_shows[temp_id] = {}
        dic_tv_shows[temp_id]['hasTitle'] = str(temp_movie['title'].encode('ascii', 'ignore').decode('ascii'))
        if 'series years' in temp_movie.keys():
            dic_tv_shows[temp_id]['hasSeasonYears'] = temp_movie['series years']
        elif 'year' in temp_movie.keys():
            dic_tv_shows[temp_id]['hasSeasonYears'] = str(temp_movie['year'])
        else:
            dic_tv_shows[temp_id]['hasSeasonYears'] = ''
        if 'number of seasons' in temp_movie.keys():
            dic_tv_shows[temp_id]['hasNumberSeasons'] = temp_movie['number of seasons']
        else:
            dic_tv_shows[temp_id]['hasNumberSeasons'] = ''
        if 'runtimes' in temp_movie.keys():
            dic_tv_shows[temp_id]['hasRuntime'] = int(re.findall(r'\d+', temp_movie['runtimes'][0])[0])
        else:
            dic_tv_shows[temp_id]['hasRuntime'] = -1
        dic_tv_shows[temp_id]['hasRating'] = temp_movie['rating']
        if 'plot' in temp_movie.keys():
            dic_tv_shows[temp_id]['hasPlot'] = str(' '.join(temp_movie['plot']).encode('ascii', 'ignore').decode('ascii'))
        else:
            dic_tv_shows[temp_id]['hasPlot'] = ''
        dic_tv_shows[temp_id]['hasID'] = temp_id
        if 'full-size cover url' in temp_movie.keys():
            dic_tv_shows[temp_id]['hasCover'] = str(temp_movie['full-size cover url'])
        else:
            dic_tv_shows[temp_id]['hasCover'] = ''
        dic_tv_shows[temp_id]['hasGenre'] = [str(x) for x in temp_movie['genres']]

        cont_person = 1
        if 'writer' in temp_movie.keys():
            add_person_to_dictionary(temp_movie['writer'][0:min(5, len(temp_movie['writer']))], 'isWriterIn', temp_id)
        cont_person = 1
        if 'director' in temp_movie.keys():
            add_person_to_dictionary(temp_movie['director'][0:min(5, len(temp_movie['director']))], 'isDirectorIn', temp_id)
        cont_person = 1
        if 'cast' in temp_movie.keys():
            add_person_to_dictionary(temp_movie['cast'][0:min(15, len(temp_movie['cast']))], 'isActorIn', temp_id)

    print "len movie: ",    len(dic_movies)
    print "len people: ",   len(dic_people)
    print "len tv show: ",  len(dic_tv_shows)

    with open('dic_movies.json', 'w') as fp:
        json.dump(dic_movies, fp)

    with open('dic_tv_shows.json', 'w') as fp:
        json.dump(dic_tv_shows, fp)

    with open('dic_people.json', 'w') as fp:
        json.dump(dic_people, fp)
