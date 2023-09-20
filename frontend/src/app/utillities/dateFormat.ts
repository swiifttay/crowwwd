export function dateFormatter(date: string) {
    const options: Intl.DateTimeFormatOptions = {year:"numeric", month: "long", day: "numeric", weekday: "long"};
    return new Date(date).toLocaleDateString("en-us", options);
}