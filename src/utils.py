from datetime import datetime

def format_gcal_date(title,start_date,end_date):
    start_date = datetime.strptime(start_date,'%Y-%m-%d')
    end_date = datetime.strptime(end_date,'%Y-%m-%d')
    formatted_start_date = format_utc_date(start_date)
    formatted_end_date = format_utc_date(end_date)
    return f'https://calendar.google.com/calendar/u/0/r/eventedit?text={title}&dates={formatted_start_date}/{formatted_end_date}'


def format_utc_date(date):
    return date.strftime("%Y%m%d") + "T" + date.strftime("%H%M%S")